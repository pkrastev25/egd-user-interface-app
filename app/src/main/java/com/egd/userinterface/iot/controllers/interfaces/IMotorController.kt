package com.egd.userinterface.iot.controllers.interfaces

/**
 * Abstraction for the controller responsible for managing the motor.
 *
 * @author Petar Krastev
 * @since 12.11.2017
 */
interface IMotorController : IController {

    /**
     * Starts the motor.
     */
    fun start()

    /**
     * Stops the motor.
     */
    fun stop()
}