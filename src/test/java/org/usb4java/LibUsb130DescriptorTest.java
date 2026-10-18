/*
 * Copyright 2026 SensorsINI
 * See LICENSE.md for licensing information.
 */

package org.usb4java;

import static org.junit.Assert.assertEquals;
import static org.usb4java.test.UsbAssume.assumeUsbTestsEnabled;
import static org.usb4java.test.UsbAssume.isUsbTestsEnabled;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Uninitialized access to descriptors added for libusb 1.0.28 through 1.0.30.
 */
public class LibUsb130DescriptorTest
{
    @Before
    public void setUp()
    {
        if (isUsbTestsEnabled())
        {
            LibUsb.init(null);
        }
    }

    @After
    public void tearDown()
    {
        if (isUsbTestsEnabled())
        {
            LibUsb.exit(null);
        }
    }

    @Test
    public void testRuntimeIsLibusb1030()
    {
        assumeUsbTestsEnabled();
        final Version version = LibUsb.getVersion();
        assertEquals(1, version.major());
        assertEquals(0, version.minor());
        assertEquals(30, version.micro());
    }

    @Test(expected = IllegalStateException.class)
    public void testUninitializedSsPlus()
    {
        assumeUsbTestsEnabled();
        new SsPlusUsbDeviceCapabilityDescriptor().numSublinkSpeedAttributes();
    }

    @Test(expected = IllegalStateException.class)
    public void testUninitializedPlatform()
    {
        assumeUsbTestsEnabled();
        new PlatformDescriptor().bLength();
    }

    @Test(expected = IllegalStateException.class)
    public void testUninitializedInterfaceAssociation()
    {
        assumeUsbTestsEnabled();
        new InterfaceAssociationDescriptors().getLength();
    }
}
