package com.egd.userinterface.constants.enums;

import android.support.annotation.IntDef;

import com.google.android.things.pio.Gpio;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Represents all the possible edge triggers types for a {@link Gpio}
 * configured as input.
 *
 * @author Petar Krastev
 * @since 6.11.2017
 */
@IntDef({
        GPIOEdgeTriggerType.EDGE_NONE,
        GPIOEdgeTriggerType.EDGE_RISING,
        GPIOEdgeTriggerType.EDGE_FALLING,
        GPIOEdgeTriggerType.EDGE_BOTH
})
@Retention(RetentionPolicy.SOURCE)
public @interface GPIOEdgeTriggerType {

    /**
     * Represents {@link Gpio#EDGE_NONE} as an edge trigger type for
     * an input {@link Gpio}, it is also the default option if none
     * is specified.
     */
    int EDGE_NONE = Gpio.EDGE_NONE;

    /**
     * Represents {@link Gpio#EDGE_RISING} as an edge trigger type.
     */
    int EDGE_RISING = Gpio.EDGE_RISING;

    /**
     * Represents {@link Gpio#EDGE_FALLING} as an edge trigger type for
     * an input {@link Gpio}.
     */
    int EDGE_FALLING = Gpio.EDGE_FALLING;

    /**
     * Represents {@link Gpio#EDGE_BOTH} as an edge trigger type for
     * an input {@link Gpio}.
     */
    int EDGE_BOTH = Gpio.EDGE_BOTH;
}
