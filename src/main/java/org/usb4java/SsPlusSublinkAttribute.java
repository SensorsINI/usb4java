/*
 * Copyright 2026 SensorsINI
 * See LICENSE.md for licensing information.
 */

package org.usb4java;

/**
 * One sublink speed attribute from a SuperSpeedPlus device capability
 * descriptor. Lane speed is {@code mantissa * 10^(3 * exponent)} bits per
 * second. Exponent 3 and mantissa 10 is one 10 Gbit/s lane.
 */
public final class SsPlusSublinkAttribute
{
    private final int ssid;
    private final int exponent;
    private final int type;
    private final int direction;
    private final int protocol;
    private final int mantissa;

    SsPlusSublinkAttribute(final int ssid, final int exponent, final int type,
        final int direction, final int protocol, final int mantissa)
    {
        this.ssid = ssid;
        this.exponent = exponent;
        this.type = type;
        this.direction = direction;
        this.protocol = protocol;
        this.mantissa = mantissa;
    }

    /** Sublink Speed Attribute ID. */
    public int ssid()
    {
        return this.ssid;
    }

    /**
     * Speed exponent. 0 = bit/s, 1 = kbit/s, 2 = Mbit/s, 3 = Gbit/s.
     * See {@link LibUsb#SSPLUS_ATTR_EXP_BPS}.
     */
    public int exponent()
    {
        return this.exponent;
    }

    /** {@link LibUsb#SSPLUS_ATTR_TYPE_SYM} or {@link LibUsb#SSPLUS_ATTR_TYPE_ASYM}. */
    public int type()
    {
        return this.type;
    }

    /** {@link LibUsb#SSPLUS_ATTR_DIR_RX} or {@link LibUsb#SSPLUS_ATTR_DIR_TX}. */
    public int direction()
    {
        return this.direction;
    }

    /** {@link LibUsb#SSPLUS_ATTR_PROT_SS} or {@link LibUsb#SSPLUS_ATTR_PROT_SSPLUS}. */
    public int protocol()
    {
        return this.protocol;
    }

    /** Mantissa applied to the exponent. */
    public int mantissa()
    {
        return this.mantissa;
    }

    /** Bit rate in bit/s, or -1 if the exponent is not one of the four defined values. */
    public long bitRate()
    {
        if (this.exponent < 0 || this.exponent > 3)
        {
            return -1L;
        }
        long rate = this.mantissa & 0xFFFFL;
        for (int i = 0; i < this.exponent; i++)
        {
            rate *= 1000L;
        }
        return rate;
    }

    @Override
    public String toString()
    {
        final String dir = this.direction == LibUsb.SSPLUS_ATTR_DIR_TX ? "TX" : "RX";
        final String kind = this.type == LibUsb.SSPLUS_ATTR_TYPE_ASYM ? "asymmetric" : "symmetric";
        final String proto = this.protocol == LibUsb.SSPLUS_ATTR_PROT_SSPLUS
            ? "SuperSpeedPlus" : "SuperSpeed";
        return String.format(
            "ssid=%d %s %s %s mantissa=%d exponent=%d (%d bit/s)",
            this.ssid, dir, kind, proto, this.mantissa, this.exponent, this.bitRate());
    }
}
