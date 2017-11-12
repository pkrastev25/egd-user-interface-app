package com.egd.userinterface.controllers.models;

/**
 * Abstraction for the controller responsible for managing the LEDs.
 *
 * @author Petar Krastev
 * @since 12.11.2017
 */
public interface ILEDController extends IController {

    /**
     * Turns on the LEDs.
     */
    void LEDsON();

    /**
     * Turns off the LEDs.
     */
    void LEDsOFF();
}
