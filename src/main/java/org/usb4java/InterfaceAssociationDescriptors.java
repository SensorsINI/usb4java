/*
 * Copyright 2026 SensorsINI
 * See LICENSE.md for licensing information.
 */

package org.usb4java;

/**
 * Array of {@link InterfaceAssociationDescriptor} values returned by libusb.
 * Free it with {@link LibUsb#freeInterfaceAssociationDescriptors}.
 */
public final class InterfaceAssociationDescriptors
{
    /** The native pointer to the array structure. */
    private long interfaceAssociationDescriptorsPointer;

    /**
     * Pass this to
     * {@link LibUsb#getInterfaceAssociationDescriptors} or
     * {@link LibUsb#getActiveInterfaceAssociationDescriptors}.
     */
    public InterfaceAssociationDescriptors()
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
        return this.interfaceAssociationDescriptorsPointer;
    }

    /** Number of descriptors in the array. */
    public native int getLength();

    /**
     * Descriptor at {@code index}. Invalid after this array is freed.
     *
     * @param index
     *            Zero-based index.
     * @return The descriptor.
     */
    public native InterfaceAssociationDescriptor get(int index);
}
