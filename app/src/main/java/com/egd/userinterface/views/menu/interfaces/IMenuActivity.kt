package com.egd.userinterface.views.menu.interfaces

import com.egd.userinterface.views.menu.MenuViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface IMenuActivity : MvpView {

    fun onPreviousMenuButtonIntent(): Observable<Unit>

    fun onNextMenuButtonIntent(): Observable<Unit>

    fun onConfirmMenuButtonIntent(): Observable<Unit>

    fun onBackMenuButtonIntent(): Observable<Unit>

    fun render(state: MenuViewState)
}