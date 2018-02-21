package com.egd.userinterface.constants.enums

import android.support.annotation.IntDef
import com.google.android.things.pio.Gpio

/**
 * Represents all the possible edge triggers types for a [Gpio]
 * configured as input.
 *
 * @author Petar Krastev
 * @since 6.11.2017
 */
@IntDef(
        InputGPIOEdgeTriggerTypesEnum.EDGE_NONE.toLong(),
        InputGPIOEdgeTriggerTypesEnum.EDGE_RISING.toLong(),
        InputGPIOEdgeTriggerTypesEnum.EDGE_FALLING.toLong(),
        InputGPIOEdgeTriggerTypesEnum.EDGE_BOTH.toLong()
)
@Retention(AnnotationRetention.SOURCE)
annotation class InputGPIOEdgeTriggerTypesEnumAnnotation

object InputGPIOEdgeTriggerTypesEnum {

    const val EDGE_NONE = Gpio.EDGE_NONE

    const val EDGE_RISING = Gpio.EDGE_RISING

    const val EDGE_FALLING = Gpio.EDGE_FALLING

    const val EDGE_BOTH = Gpio.EDGE_BOTH
}
