package com.egd.userinterface.views.menu.interfaces

import com.egd.userinterface.views.menu.MenuViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
interface IMenuActivity : MvpView {

    fun menuButtonInitStateIntent(): Observable<Unit>

    fun previousMenuButtonIntent(): Observable<Unit>

    fun nextMenuButtonIntent(): Observable<Unit>

    fun confirmMenuButtonIntent(): Observable<Unit>

    fun backMenuButtonIntent(): Observable<Unit>

    fun render(state: MenuViewState)
}