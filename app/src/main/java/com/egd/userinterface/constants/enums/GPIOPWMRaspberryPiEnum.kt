package com.egd.userinterface.constants.enums

import android.support.annotation.StringDef

/**
 * Contains all GPIO ports that can be used as PWM outputs for the
 * Raspberry Pi 3.
 *
 * @author Petar Krastev
 * @since 24.11.2017
 */
@Retention(AnnotationRetention.SOURCE)
@StringDef(
        GPIOPWMRaspberryPiEnum.PWM_PIN12,
        GPIOPWMRaspberryPiEnum.PWM_PIN33
)
annotation class GPIOPWMRaspberryPiEnumAnnotation

object GPIOPWMRaspberryPiEnum {

    /**
     * Represents pin 12 on the board which can be used as a PWM output.
     *
     * @see GPIOPortsRaspberryPiEnum.GPIO_PIN12
     */
    const val PWM_PIN12 = "PWM0"

    /**
     * Represents pin 33 on the board which can be used as a PWM output.
     *
     * @see GPIOPortsRaspberryPiEnum.GPIO_PIN33
     */
    const val PWM_PIN33 = "PWM1"
}
