package com.egd.userinterface.views.menu.interfaces

import com.egd.userinterface.views.menu.MenuIntent
import com.egd.userinterface.views.menu.MenuViewState

/**
 * @author Petar Krastev
 */
interface IMenuViewStateReducer {

    fun reduceState(
            previousState: MenuViewState,
            changes: MenuIntent
    ): MenuViewState
}