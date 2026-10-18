/*
 * Copyright 2026 SensorsINI
 * See LICENSE.md for licensing information.
 */

package org.usb4java;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Interface Association Descriptor. Groups contiguous interfaces into one
 * function. Documented in section 9.6.4 of the USB 3.0 specification.
 *
 * Instances point into an {@link InterfaceAssociationDescriptors} array.
 * They are invalid after that array is freed.
 */
public final class InterfaceAssociationDescriptor
{
    /** The native pointer to the descriptor structure. */
    private long interfaceAssociationDescriptorPointer;

    /** Created by native code. */
    public InterfaceAssociationDescriptor()
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
        return this.interfaceAssociationDescriptorPointer;
    }

    /** Size of this descriptor in bytes. */
    public native byte bLength();

    /** Descriptor type. {@link LibUsb#DT_INTERFACE_ASSOCIATION}. */
    public native byte bDescriptorType();

    /** First interface number in this function. */
    public native byte bFirstInterface();

    /** Number of contiguous interfaces in this function. */
    public native byte bInterfaceCount();

    /** Function class code. */
    public native byte bFunctionClass();

    /** Function subclass code. */
    public native byte bFunctionSubClass();

    /** Function protocol code. */
    public native byte bFunctionProtocol();

    /** String descriptor index for this function. */
    public native byte iFunction();

    /**
     * Returns a dump of this descriptor.
     *
     * @return The descriptor dump.
     */
    public String dump()
    {
        return String.format(
            "Interface Association Descriptor:%n" +
            "  bLength %18d%n" +
            "  bDescriptorType %10d%n" +
            "  bFirstInterface %9d%n" +
            "  bInterfaceCount %9d%n" +
            "  bFunctionClass %10d%n" +
            "  bFunctionSubClass %7d%n" +
            "  bFunctionProtocol %7d%n" +
            "  iFunction %15d%n",
            this.bLength() & 0xFF,
            this.bDescriptorType() & 0xFF,
            this.bFirstInterface() & 0xFF,
            this.bInterfaceCount() & 0xFF,
            this.bFunctionClass() & 0xFF,
            this.bFunctionSubClass() & 0xFF,
            this.bFunctionProtocol() & 0xFF,
            this.iFunction() & 0xFF);
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
            .append(this.bLength())
            .append(this.bDescriptorType())
            .append(this.bFirstInterface())
            .append(this.bInterfaceCount())
            .append(this.bFunctionClass())
            .append(this.bFunctionSubClass())
            .append(this.bFunctionProtocol())
            .append(this.iFunction())
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
        final InterfaceAssociationDescriptor other = (InterfaceAssociationDescriptor) obj;
        return new EqualsBuilder()
            .append(this.bLength(), other.bLength())
            .append(this.bDescriptorType(), other.bDescriptorType())
            .append(this.bFirstInterface(), other.bFirstInterface())
            .append(this.bInterfaceCount(), other.bInterfaceCount())
            .append(this.bFunctionClass(), other.bFunctionClass())
            .append(this.bFunctionSubClass(), other.bFunctionSubClass())
            .append(this.bFunctionProtocol(), other.bFunctionProtocol())
            .append(this.iFunction(), other.iFunction())
            .isEquals();
    }

    @Override
    public String toString()
    {
        return this.dump();
    }
}
