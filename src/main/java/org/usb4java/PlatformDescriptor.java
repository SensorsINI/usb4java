/*
 * Copyright 2026 SensorsINI
 * See LICENSE.md for licensing information.
 */

package org.usb4java;

import java.nio.ByteBuffer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Platform capability descriptor from the BOS. Documented in section 9.6.2.4
 * of the USB 3.2 specification.
 */
public final class PlatformDescriptor
{
    /** The native pointer to the descriptor structure. */
    private long platformDescriptorPointer;

    /**
     * Pass this to {@link LibUsb#getPlatformDescriptor}.
     */
    public PlatformDescriptor()
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
        return this.platformDescriptorPointer;
    }

    /** Size of this descriptor in bytes. */
    public native byte bLength();

    /** Descriptor type. */
    public native byte bDescriptorType();

    /** Device capability type. {@link LibUsb#BT_PLATFORM_DESCRIPTOR}. */
    public native byte bDevCapabilityType();

    /** Reserved field. */
    public native byte bReserved();

    /** 128-bit platform capability UUID. */
    public native ByteBuffer platformCapabilityUUID();

    /** Vendor-defined bytes after the UUID. */
    public native ByteBuffer capabilityData();

    /**
     * Returns a dump of this descriptor.
     *
     * @return The descriptor dump.
     */
    public String dump()
    {
        return String.format(
            "Platform Descriptor:%n" +
            "  bLength %18d%n" +
            "  bDescriptorType %10d%n" +
            "  bDevCapabilityType %7d%n" +
            "  bReserved %16d%n" +
            "  PlatformCapabilityUUID:%n%s%n" +
            "  CapabilityData:%n%s%n",
            this.bLength() & 0xFF,
            this.bDescriptorType() & 0xFF,
            this.bDevCapabilityType() & 0xFF,
            this.bReserved() & 0xFF,
            DescriptorUtils.dump(this.platformCapabilityUUID()).replaceAll("(?m)^", "    "),
            DescriptorUtils.dump(this.capabilityData()).replaceAll("(?m)^", "    "));
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
            .append(this.bLength())
            .append(this.bDescriptorType())
            .append(this.bDevCapabilityType())
            .append(this.bReserved())
            .append(this.platformCapabilityUUID())
            .append(this.capabilityData())
            .toHashCode();
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
        final PlatformDescriptor other = (PlatformDescriptor) obj;
        return new EqualsBuilder()
            .append(this.bLength(), other.bLength())
            .append(this.bDescriptorType(), other.bDescriptorType())
            .append(this.bDevCapabilityType(), other.bDevCapabilityType())
            .append(this.bReserved(), other.bReserved())
            .append(this.platformCapabilityUUID(), other.platformCapabilityUUID())
            .append(this.capabilityData(), other.capabilityData())
            .isEquals();
    }

    @Override
    public String toString()
    {
        return this.dump();
    }
}
