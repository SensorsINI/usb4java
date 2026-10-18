/*
 * Copyright 2026 SensorsINI
 * See LICENSE.md for licensing information.
 */

package org.usb4java;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Bit-rate math for a SuperSpeedPlus sublink. Does not load libusb.
 */
public class SsPlusSublinkAttributeTest
{
    /**
     * USB 3.2 Gen 2 lane: mantissa 10, exponent gigabits.
     */
    @Test
    public void testTenGigabitLane()
    {
        final SsPlusSublinkAttribute lane = new SsPlusSublinkAttribute(
            0, 3, 0, 0, 1, 10);
        assertEquals(10000000000L, lane.bitRate());
    }

    /**
     * USB 3.2 Gen 1 lane: mantissa 5, exponent gigabits.
     */
    @Test
    public void testFiveGigabitLane()
    {
        final SsPlusSublinkAttribute lane = new SsPlusSublinkAttribute(
            0, 3, 0, 0, 0, 5);
        assertEquals(5000000000L, lane.bitRate());
    }
}
