package com.egd.userinterface.iot.constants.enums

import com.google.android.things.pio.Gpio

/**
 * Represents all the possible edge triggers types for a [Gpio]
 * configured as input.
 *
 * @author Petar Krastev
 * @since 6.11.2017
 */
object IotInputGpioEdgeTriggerTypesEnum {

    const val EDGE_NONE = Gpio.EDGE_NONE

    const val EDGE_RISING = Gpio.EDGE_RISING

    const val EDGE_FALLING = Gpio.EDGE_FALLING

    const val EDGE_BOTH = Gpio.EDGE_BOTH
}
