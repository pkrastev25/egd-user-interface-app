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
        GPIOPortsRaspberryPi.GPIO_PIN3,
        GPIOPortsRaspberryPi.GPIO_PIN5,
        GPIOPortsRaspberryPi.GPIO_PIN7,
        GPIOPortsRaspberryPi.GPIO_PIN29,
        GPIOPortsRaspberryPi.GPIO_PIN31,
        GPIOPortsRaspberryPi.GPIO_PIN26,
        GPIOPortsRaspberryPi.GPIO_PIN24,
        GPIOPortsRaspberryPi.GPIO_PIN21,
        GPIOPortsRaspberryPi.GPIO_PIN19,
        GPIOPortsRaspberryPi.GPIO_PIN23,
        GPIOPortsRaspberryPi.GPIO_PIN32,
        GPIOPortsRaspberryPi.GPIO_PIN33,
        GPIOPortsRaspberryPi.GPIO_PIN8,
        GPIOPortsRaspberryPi.GPIO_PIN10,
        GPIOPortsRaspberryPi.GPIO_PIN36,
        GPIOPortsRaspberryPi.GPIO_PIN11,
        GPIOPortsRaspberryPi.GPIO_PIN12,
        GPIOPortsRaspberryPi.GPIO_PIN35,
        GPIOPortsRaspberryPi.GPIO_PIN38,
        GPIOPortsRaspberryPi.GPIO_PIN40,
        GPIOPortsRaspberryPi.GPIO_PIN15,
        GPIOPortsRaspberryPi.GPIO_PIN16,
        GPIOPortsRaspberryPi.GPIO_PIN18,
        GPIOPortsRaspberryPi.GPIO_PIN22,
        GPIOPortsRaspberryPi.GPIO_PIN37,
        GPIOPortsRaspberryPi.GPIO_PIN13
})
public @interface GPIOPortsRaspberryPi {

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of I2C1 (SDA).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN3 = "BCM2";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of I2C1 (SCL).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN5 = "BCM3";

    /**
     * Represents a GPIO port on the board.
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN7 = "BCM4";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of UART0 (TXD) and MINIUART (TXD).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN8 = "BCM14";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of UART0 (RXD) and MINIUART (RXD).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN10 = "BCM15";

    /**
     * Represents a GPIO port on the board.
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN11 = "BCM17";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of I2S1 (BCLK) and PWM0.
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN12 = "BCM18";

    /**
     * Represents a GPIO port on the board.
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN13 = "BCM27";

    /**
     * Represents a GPIO port on the board.
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN15 = "BCM22";

    /**
     * Represents a GPIO port on the board.
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN16 = "BCM23";

    /**
     * Represents a GPIO port on the board.
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN18 = "BCM24";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of SPI0 (MOSI).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN19 = "BCM10";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of SPI0 (MISO).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN21 = "BCM9";

    /**
     * Represents a GPIO port on the board.
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN22 = "BCM25";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of SPI0 (SCLK).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN23 = "BCM11";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of SPI0 (SS0).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN24 = "BCM8";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of SPI0 (SS1).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN26 = "BCM7";

    /**
     * Represents a GPIO port on the board.
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN29 = "BCM5";

    /**
     * Represents a GPIO port on the board.
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN31 = "BCM6";

    /**
     * Represents a GPIO port on the board.
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN32 = "BCM12";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of PWM1.
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN33 = "BCM13";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of I2S1 (LRCLK).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN35 = "BCM19";

    /**
     * Represents a GPIO port on the board.
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN36 = "BCM16";

    /**
     * Represents a GPIO port on the board.
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN37 = "BCM26";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of I2S1 (SDIN).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN38 = "BCM20";

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of I2S1 (SDOUT).
     *
     * @see <a href="https://developer.android.com/things/hardware/raspberrypi-io.html">GPIO Documentation</a>
     */
    String GPIO_PIN40 = "BCM21";
}
