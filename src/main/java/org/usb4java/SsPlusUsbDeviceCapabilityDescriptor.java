/*
 * Copyright 2026 SensorsINI
 * See LICENSE.md for licensing information.
 */

package org.usb4java;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Parsed SuperSpeedPlus USB Device Capability descriptor (USB 3.1 / 3.2).
 *
 * libusb's parsed structure does not repeat {@code bLength},
 * {@code bDescriptorType}, or {@code bDevCapabilityType}. Those bytes stay on
 * the parent {@link BosDevCapabilityDescriptor}. This descriptor is documented
 * in section 9.6.2.5 of the USB 3.1 specification. Lane count and each
 * sublink's mantissa and exponent are what distinguish USB 3.2 Gen 1x2,
 * Gen 2x1, and Gen 2x2. {@link LibUsb#getDeviceSpeed(Device)} cannot tell
 * Gen 1x2 from Gen 2x1; both are 10 Gbit/s.
 */
public final class SsPlusUsbDeviceCapabilityDescriptor
{
    /** The native pointer to the descriptor structure. */
    private long ssPlusUsbDeviceCapabilityDescriptorPointer;

    /**
     * Pass this to
     * {@link LibUsb#getSsPlusUsbDeviceCapabilityDescriptor}.
     */
    public SsPlusUsbDeviceCapabilityDescriptor()
    {
        // Empty
    }

    /**
     * Returns the native pointer.
     *
     * @return The native pointer.
     */
    public long getPointer()
    {
        return this.ssPlusUsbDeviceCapabilityDescriptorPointer;
    }

    /** Number of sublink speed attributes, including RX and TX. */
    public native byte numSublinkSpeedAttributes();

    /** Number of distinct sublink speed IDs. */
    public native byte numSublinkSpeedIDs();

    /** Minimum functional lane speed ID. */
    public native byte ssid();

    /** Minimum receive lane count. */
    public native byte minRxLaneCount();

    /** Minimum transmit lane count. */
    public native byte minTxLaneCount();

    /** Sublink speed ID at {@code index}. */
    public native byte sublinkSsid(int index);

    /** Exponent at {@code index}. See {@link LibUsb#SSPLUS_ATTR_EXP_BPS}. */
    public native int sublinkExponent(int index);

    /** Symmetric or asymmetric at {@code index}. */
    public native int sublinkType(int index);

    /** RX or TX at {@code index}. */
    public native int sublinkDirection(int index);

    /** SuperSpeed or SuperSpeedPlus protocol at {@code index}. */
    public native int sublinkProtocol(int index);

    /** Mantissa at {@code index}. */
    public native int sublinkMantissa(int index);

    /**
     * One sublink attribute.
     *
     * @param index
     *            Zero-based index, from 0 to
     *            {@link #numSublinkSpeedAttributes()} - 1.
     * @return The attribute.
     */
    public SsPlusSublinkAttribute sublink(final int index)
    {
        return new SsPlusSublinkAttribute(
            this.sublinkSsid(index) & 0xFF,
            this.sublinkExponent(index),
            this.sublinkType(index),
            this.sublinkDirection(index),
            this.sublinkProtocol(index),
            this.sublinkMantissa(index));
    }

    /**
     * Returns a dump of this descriptor.
     *
     * @return The descriptor dump.
     */
    public String dump()
    {
        final StringBuilder text = new StringBuilder();
        final int count = this.numSublinkSpeedAttributes() & 0xFF;
        text.append(String.format(
            "SuperSpeedPlus USB Device Capability Descriptor:%n" +
            "  numSublinkSpeedAttributes %d%n" +
            "  numSublinkSpeedIDs %d%n" +
            "  ssid %d%n" +
            "  minRxLaneCount %d%n" +
            "  minTxLaneCount %d%n",
            count,
            this.numSublinkSpeedIDs() & 0xFF,
            this.ssid() & 0xFF,
            this.minRxLaneCount() & 0xFF,
            this.minTxLaneCount() & 0xFF));
        for (int i = 0; i < count; i++)
        {
            text.append("  sublink[").append(i).append("] ")
                .append(this.sublink(i)).append('\n');
        }
        return text.toString();
    }

    @Override
    public int hashCode()
    {
        final HashCodeBuilder builder = new HashCodeBuilder()
            .append(this.numSublinkSpeedAttributes())
            .append(this.numSublinkSpeedIDs())
            .append(this.ssid())
            .append(this.minRxLaneCount())
            .append(this.minTxLaneCount());
        final int count = this.numSublinkSpeedAttributes() & 0xFF;
        for (int i = 0; i < count; i++)
        {
            builder.append(this.sublinkMantissa(i)).append(this.sublinkExponent(i))
                .append(this.sublinkSsid(i));
        }
        return builder.toHashCode();
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null || obj.getClass() != this.getClass())
        {
            return false;
        }
        if (obj == this)
        {
            return true;
        }
        final SsPlusUsbDeviceCapabilityDescriptor other =
            (SsPlusUsbDeviceCapabilityDescriptor) obj;
        if (!new EqualsBuilder()
            .append(this.numSublinkSpeedAttributes(), other.numSublinkSpeedAttributes())
            .append(this.numSublinkSpeedIDs(), other.numSublinkSpeedIDs())
            .append(this.ssid(), other.ssid())
            .append(this.minRxLaneCount(), other.minRxLaneCount())
            .append(this.minTxLaneCount(), other.minTxLaneCount())
            .isEquals())
        {
            return false;
        }
        final int count = this.numSublinkSpeedAttributes() & 0xFF;
        for (int i = 0; i < count; i++)
        {
            if (!this.sublink(i).toString().equals(other.sublink(i).toString()))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString()
    {
        return this.dump();
    }
}
