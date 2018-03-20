package com.egd.userinterface.iot.controllers.interfaces

/**
 * Abstraction for the controller responsible for managing the LEDs.
 *
 * @author Petar Krastev
 * @since 12.11.2017
 */
interface ILEDController : IController {

    /**
     * Turns on the LEDs.
     */
    fun LEDsON()

    /**
     * Turns off the LEDs.
     */
    fun LEDsOFF()
}
