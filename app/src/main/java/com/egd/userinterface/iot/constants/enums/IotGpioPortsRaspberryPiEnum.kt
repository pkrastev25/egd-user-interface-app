package com.egd.userinterface.iot.constants.enums

/**
 * Contains all GPIO ports on the Raspberry Pi 3.
 *
 * @author Petar Krastev
 * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
 *
 * @since 26.10.2017
 */
object IotGpioPortsRaspberryPiEnum {
    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of I2C1 (SDA).
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN3 = "BCM2"

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of I2C1 (SCL).
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN5 = "BCM3"

    /**
     * Represents a GPIO port on the board.
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN7 = "BCM4"

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of UART0 (TXD) and MINIUART (TXD).
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN8 = "BCM14"

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of UART0 (RXD) and MINIUART (RXD).
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN10 = "BCM15"

    /**
     * Represents a GPIO port on the board.
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN11 = "BCM17"

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of I2S1 (BCLK) and PWM0.
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN12 = "BCM18"

    /**
     * Represents a GPIO port on the board.
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN13 = "BCM27"

    /**
     * Represents a GPIO port on the board.
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN15 = "BCM22"

    /**
     * Represents a GPIO port on the board.
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN16 = "BCM23"

    /**
     * Represents a GPIO port on the board.
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN18 = "BCM24"

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of SPI0 (MOSI).
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN19 = "BCM10"

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of SPI0 (MISO).
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN21 = "BCM9"

    /**
     * Represents a GPIO port on the board.
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN22 = "BCM25"

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of SPI0 (SCLK).
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN23 = "BCM11"

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of SPI0 (SS0).
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN24 = "BCM8"

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of SPI0 (SS1).
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN26 = "BCM7"

    /**
     * Represents a GPIO port on the board.
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN29 = "BCM5"

    /**
     * Represents a GPIO port on the board.
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN31 = "BCM6"

    /**
     * Represents a GPIO port on the board.
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN32 = "BCM12"

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of PWM1.
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN33 = "BCM13"

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of I2S1 (LRCLK).
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN35 = "BCM19"

    /**
     * Represents a GPIO port on the board.
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN36 = "BCM16"

    /**
     * Represents a GPIO port on the board.
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN37 = "BCM26"

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of I2S1 (SDIN).
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN38 = "BCM20"

    /**
     * Represents a GPIO port on the board. Contains the alternate
     * function of I2S1 (SDOUT).
     *
     * @see [GPIO Documentation](https://developer.android.com/things/hardware/raspberrypi-io.html)
     */
    const val GPIO_PIN40 = "BCM21"
}
