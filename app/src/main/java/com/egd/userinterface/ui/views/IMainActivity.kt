package com.egd.userinterface.ui.views

import com.egd.userinterface.view.states.MenuViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface IMainActivity : MvpView {

    fun onPreviousMenuButtonIntent(): Observable<Unit>

    fun onNextMenuButtonIntent(): Observable<Unit>

    fun onConfirmMenuButtonIntent(): Observable<Unit>

    fun onBackMenuButtonIntent(): Observable<Unit>

    fun render(state: MenuViewState)
}