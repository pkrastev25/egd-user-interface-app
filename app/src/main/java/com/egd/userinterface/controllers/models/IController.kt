package com.egd.userinterface.controllers.models

/**
 * Base abstraction for all controllers. Place methods here that every
 * controller class should have.
 *
 * @author Petar Krastev
 * @since 12.11.2017
 */
interface IController {

    /**
     * Releases all resources held by the controller class.
     */
    fun clean()
}
