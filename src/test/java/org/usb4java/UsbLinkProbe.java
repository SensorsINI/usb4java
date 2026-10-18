/*
 * Copyright 2026 SensorsINI
 * See LICENSE.md for licensing information.
 */

package org.usb4java;

/**
 * Prints negotiated speed and SuperSpeedPlus sublinks for attached devices.
 * Used to check a USB 3.2 Gen 2x2 camera against libusb 1.0.30.
 */
public final class UsbLinkProbe
{
    private UsbLinkProbe()
    {
    }

    public static void main(final String[] args)
    {
        final int init = LibUsb.init(null);
        if (init != LibUsb.SUCCESS)
        {
            System.out.println("init failed: " + init);
            return;
        }
        try
        {
            final Version version = LibUsb.getVersion();
            System.out.println("libusb " + version.major() + "." + version.minor()
                + "." + version.micro()
                + " hotplug=" + LibUsb.hasCapability(LibUsb.CAP_HAS_HOTPLUG));
            final DeviceList list = new DeviceList();
            final int count = LibUsb.getDeviceList(null, list);
            if (count < 0)
            {
                System.out.println("getDeviceList failed: " + count);
                return;
            }
            try
            {
                for (Device device : list)
                {
                    printDevice(device);
                }
            }
            finally
            {
                LibUsb.freeDeviceList(list, true);
            }
        }
        finally
        {
            LibUsb.exit(null);
        }
    }

    private static void printDevice(final Device device)
    {
        final DeviceDescriptor descriptor = new DeviceDescriptor();
        if (LibUsb.getDeviceDescriptor(device, descriptor) != LibUsb.SUCCESS)
        {
            return;
        }
        final int vid = descriptor.idVendor() & 0xFFFF;
        final int pid = descriptor.idProduct() & 0xFFFF;
        final int speed = LibUsb.getDeviceSpeed(device);
        System.out.println(String.format(
            "%04x:%04x speed=%d bcdUSB=%04x",
            Integer.valueOf(vid), Integer.valueOf(pid), Integer.valueOf(speed),
            Integer.valueOf(descriptor.bcdUSB() & 0xFFFF)));
        if (vid == 0x04B4 && (pid == 0x00F0 || pid == 0x00F1))
        {
            dumpBos(device);
        }
        else if (speed >= LibUsb.SPEED_SUPER)
        {
            dumpBos(device);
        }
    }

    private static void dumpBos(final Device device)
    {
        final DeviceHandle handle = new DeviceHandle();
        final int opened = LibUsb.open(device, handle);
        if (opened != LibUsb.SUCCESS)
        {
            System.out.println("  open failed: " + opened);
            return;
        }
        try
        {
            final BosDescriptor bos = new BosDescriptor();
            final int result = LibUsb.getBosDescriptor(handle, bos);
            if (result != LibUsb.SUCCESS)
            {
                System.out.println("  bos failed: " + result);
                return;
            }
            try
            {
                final BosDevCapabilityDescriptor[] caps = bos.devCapability();
                for (int i = 0; caps != null && i < caps.length; i++)
                {
                    final int type = caps[i].bDevCapabilityType() & 0xFF;
                    System.out.println("  cap type=" + type);
                    if (type == (LibUsb.BT_SUPERSPEED_PLUS_CAPABILITY & 0xFF))
                    {
                        final SsPlusUsbDeviceCapabilityDescriptor ssp =
                            new SsPlusUsbDeviceCapabilityDescriptor();
                        final int parsed = LibUsb.getSsPlusUsbDeviceCapabilityDescriptor(
                            null, caps[i], ssp);
                        if (parsed != LibUsb.SUCCESS)
                        {
                            System.out.println("  ssp parse failed: " + parsed);
                        }
                        else
                        {
                            System.out.print(ssp.dump());
                            LibUsb.freeSsPlusUsbDeviceCapabilityDescriptor(ssp);
                        }
                    }
                }
            }
            finally
            {
                LibUsb.freeBosDescriptor(bos);
            }
        }
        finally
        {
            LibUsb.close(handle);
        }
    }
}
