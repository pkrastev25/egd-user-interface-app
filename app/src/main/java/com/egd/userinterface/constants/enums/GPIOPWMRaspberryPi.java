package com.egd.userinterface.constants.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Contains all GPIO ports that can be used as PWM outputs for the
 * Raspberry Pi 3.
 *
 * @author Petar Krastev
 * @since 24.11.2017
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({
        GPIOPWMRaspberryPi.PWM_PIN12,
        GPIOPWMRaspberryPi.PWM_PIN33
})
public @interface GPIOPWMRaspberryPi {

    /**
     * Represents pin 12 on the board which can be used as a PWM output.
     *
     * @see GPIOPortsRaspberryPi#GPIO_PIN12
     */
    String PWM_PIN12 = "PWM0";

    /**
     * Represents pin 33 on the board which can be used as a PWM output.
     *
     * @see GPIOPortsRaspberryPi#GPIO_PIN33
     */
    String PWM_PIN33 = "PWM1";
}
