package com.egd.userinterface.controllers.models;

/**
 * Abstraction for the controller responsible for managing the motor.
 *
 * @author Petar Krastev
 * @since 12.11.2017
 */
public interface IMotorController extends IController {

    /**
     * Starts the motor.
     */
    void start();

    /**
     * Stops the motor.
     */
    void stop();
}
