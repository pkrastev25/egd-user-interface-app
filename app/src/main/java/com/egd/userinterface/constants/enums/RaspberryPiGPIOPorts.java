package com.egd.userinterface.constants.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Contains all GPIO ports on the Raspberry Pi 3.
 *
 * @author Petar Krastev
 * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
 * @since 26.10.2017
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        RaspberryPiGPIOPorts.BCM2,
        RaspberryPiGPIOPorts.BCM3,
        RaspberryPiGPIOPorts.BCM7,
        RaspberryPiGPIOPorts.BCM8,
        RaspberryPiGPIOPorts.BCM9,
        RaspberryPiGPIOPorts.BCM10,
        RaspberryPiGPIOPorts.BCM11,
        RaspberryPiGPIOPorts.BCM13,
        RaspberryPiGPIOPorts.BCM14,
        RaspberryPiGPIOPorts.BCM15,
        RaspberryPiGPIOPorts.BCM18,
        RaspberryPiGPIOPorts.BCM19,
        RaspberryPiGPIOPorts.BCM20,
        RaspberryPiGPIOPorts.BCM21
})
public @interface RaspberryPiGPIOPorts {

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of I2C1 (SDA).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String BCM2 = "BCM2";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of I2C1 (SCL).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String BCM3 = "BCM3";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of SPI0 (SS1).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String BCM7 = "BCM7";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of SPI0 (SS0).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String BCM8 = "BCM8";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of SPI0 (MISO).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String BCM9 = "BCM9";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of SPI0 (MOSI).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String BCM10 = "BCM10";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of SPI0 (SCLK).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String BCM11 = "BCM11";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of PWM1.
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String BCM13 = "BCM13";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of UART0 (TXD) and MINIUART (TXD).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String BCM14 = "BCM14";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of UART0 (RXD) and MINIUART (RXD).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String BCM15 = "BCM15";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of I2S1 (BCLK) and PWM0.
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String BCM18 = "BCM18";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of I2S1 (LRCLK).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String BCM19 = "BCM19";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of I2S1 (SDIN).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String BCM20 = "BCM20";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of I2S1 (SDOUT).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String BCM21 = "BCM21";
}
