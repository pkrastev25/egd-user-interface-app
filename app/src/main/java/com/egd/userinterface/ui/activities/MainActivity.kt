package com.egd.userinterface.ui.activities

import android.os.Bundle
import android.view.View
import com.egd.userinterface.R
import com.egd.userinterface.ui.presenters.MainPresenter
import com.egd.userinterface.ui.views.IMainActivity
import com.egd.userinterface.view.states.MenuViewState
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_initialization.*
import kotlinx.android.synthetic.main.view_initialization_error.*
import kotlinx.android.synthetic.main.view_menu.*

class MainActivity : MviActivity<IMainActivity, MainPresenter>(), IMainActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun createPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun render(state: MenuViewState) {
        when (state) {
            is MenuViewState.InitializationState -> renderInitializationState()
            is MenuViewState.CurrentMenuState -> renderCurrentMenuState(state)
            is MenuViewState.ErrorState -> renderErrorState()
            MenuViewState.InitializationErrorState -> renderInitializationErrorState()
        }
    }

    override fun onPreviousMenuButtonIntent(): Observable<Unit> {
        return previous_menu_button.clicks()
    }

    override fun onNextMenuButtonIntent(): Observable<Unit> {
        return next_menu_button.clicks()
    }

    override fun onConfirmMenuButtonIntent(): Observable<Unit> {
        return confirm_menu_button.clicks()
    }

    override fun onBackMenuButtonIntent(): Observable<Unit> {
        return back_menu_button.clicks()
    }

    private fun renderInitializationState() {
        initialization_view.visibility = View.VISIBLE
        initialization_error_view.visibility = View.GONE
        menu_view.visibility = View.GONE
    }

    private fun renderInitializationErrorState() {
        initialization_view.visibility = View.GONE
        initialization_error_view.visibility = View.VISIBLE
        menu_view.visibility = View.GONE
    }

    private fun renderCurrentMenuState(state: MenuViewState.CurrentMenuState) {
        initialization_view.visibility = View.GONE
        initialization_error_view.visibility = View.GONE
        menu_view.visibility = View.VISIBLE
        current_menu_state_view.text = state.currentMenuState
    }

    private fun renderErrorState() {
        initialization_view.visibility = View.GONE
        initialization_error_view.visibility = View.GONE
        menu_view.visibility = View.VISIBLE
    }
}
