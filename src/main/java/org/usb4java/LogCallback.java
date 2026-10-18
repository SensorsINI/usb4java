/*
 * Copyright 2026 SensorsINI
 * See LICENSE.md for licensing information.
 */

package org.usb4java;

/**
 * Receives libusb log lines.
 *
 * libusb may call this from an internal thread. Keep the method short.
 */
public interface LogCallback
{
    /**
     * @param context
     *            Context this message belongs to, or null for a global message.
     * @param level
     *            One of the {@code LibUsb.LOG_LEVEL_*} constants.
     * @param message
     *            The log text.
     */
    void onLog(Context context, int level, String message);
}
