package com.egd.userinterface.constants.enums;

import android.support.annotation.IntDef;

import com.google.android.things.pio.Gpio;

/**
 * Contains the active types of a GPIO port.
 *
 * @author Petar Krastev
 * @since 26.10.2017
 */
@IntDef({
        GPIOActiveTypes.LOW,
        GPIOActiveTypes.HIGH
})
public @interface GPIOActiveTypes {

    /**
     * Low voltage is considered active, {@link Gpio#ACTIVE_LOW}.
     */
    int LOW = Gpio.ACTIVE_LOW;

    /**
     * High voltage is considered active, {@link Gpio#ACTIVE_HIGH}.
     */
    int HIGH = Gpio.ACTIVE_HIGH;
}
