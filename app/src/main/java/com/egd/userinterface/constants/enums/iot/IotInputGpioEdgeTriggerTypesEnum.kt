package com.egd.userinterface.constants.enums.iot

import com.google.android.things.pio.Gpio

/**
 * Refer to https://developer.android.com/things/hardware/raspberrypi-io.html.
 *
 * @author Petar Krastev
 */
object IotInputGpioEdgeTriggerTypesEnum {

    const val EDGE_NONE = Gpio.EDGE_NONE
    const val EDGE_RISING = Gpio.EDGE_RISING
    const val EDGE_FALLING = Gpio.EDGE_FALLING
    const val EDGE_BOTH = Gpio.EDGE_BOTH
}
