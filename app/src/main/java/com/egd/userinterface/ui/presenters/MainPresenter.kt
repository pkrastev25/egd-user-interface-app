package com.egd.userinterface.ui.presenters

import com.egd.userinterface.ui.views.IMainActivity
import com.egd.userinterface.view.states.MenuViewState
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable

class MainPresenter : MviBasePresenter<IMainActivity, MenuViewState>() {

    override fun bindIntents() {
        val previousMenuButtonIntent: Observable<MenuViewState> = intent(IMainActivity::onPreviousMenuButtonIntent)
                .map {
                    MenuViewState.CurrentMenuState("Previous button clicked!")
                }

        val nextMenuButtonIntent: Observable<MenuViewState> = intent(IMainActivity::onNextMenuButtonIntent)
                .map {
                    MenuViewState.CurrentMenuState("Next button clicked!")
                }

        val backMenuButtonIntent: Observable<MenuViewState> = intent(IMainActivity::onBackMenuButtonIntent)
                .map {
                    MenuViewState.CurrentMenuState("Back button clicked!")
                }

        val confirmMenuButtonIntent: Observable<MenuViewState> = intent(IMainActivity::onConfirmMenuButtonIntent)
                .map {
                    MenuViewState.CurrentMenuState("Confirm button clicked!")
                }

        val allIntents = Observable.merge(
                previousMenuButtonIntent,
                nextMenuButtonIntent,
                backMenuButtonIntent,
                confirmMenuButtonIntent
        )

        subscribeViewState(allIntents, IMainActivity::render)
    }
}