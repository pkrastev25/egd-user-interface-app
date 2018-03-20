package com.egd.userinterface.views.menu

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.egd.userinterface.R
import com.egd.userinterface.views.menu.interfaces.IMenuActivity
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_menu_init.*
import kotlinx.android.synthetic.main.view_menu_init_error.*
import kotlinx.android.synthetic.main.view_menu_options.*

class MenuActivity : MviActivity<IMenuActivity, MenuPresenter>(), IMenuActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }

    override fun createPresenter(): MenuPresenter {
        return MenuPresenter(applicationContext)
    }

    override fun render(state: MenuViewState) {
        when (state) {
            is MenuViewState.InitState -> {
                renderInitState()
            }
            MenuViewState.InitErrorState -> {
                renderInitErrorState()
            }
            is MenuViewState.OptionSpeechRecognitionStartState -> {
                renderOptionMenuState(state.stateMessage, state.error)
            }
            is MenuViewState.SpeechRecognitionState -> {
                renderSpeechRecognitionState()
            }
            is MenuViewState.OptionExternalLedState -> {
                renderOptionMenuState(state.stateMessage, state.error)
            }
            is MenuViewState.OptionExternalLedTurnLedOnState -> {
                renderOptionMenuState(state.stateMessage, state.error)
            }
            is MenuViewState.OptionExternalLedTurnLedOffState -> {
                renderOptionMenuState(state.stateMessage, state.error)
            }
            is MenuViewState.OptionExternalMotorState -> {
                renderOptionMenuState(state.stateMessage, state.error)
            }
            is MenuViewState.OptionExternalMotorStartMotorState -> {
                renderOptionMenuState(state.stateMessage, state.error)
            }
            is MenuViewState.OptionExternalMotorStopMotorState -> {
                renderOptionMenuState(state.stateMessage, state.error)
            }
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

    private fun renderInitState() {
        initialization_view.visibility = View.VISIBLE
        initialization_error_view.visibility = View.GONE
        menu_view.visibility = View.GONE
    }

    private fun renderInitErrorState() {
        initialization_view.visibility = View.GONE
        initialization_error_view.visibility = View.VISIBLE
        menu_view.visibility = View.GONE
    }

    private fun renderOptionMenuState(stateMessage: String, error: Throwable?) {
        initialization_view.visibility = View.GONE
        initialization_error_view.visibility = View.GONE
        menu_view.visibility = View.VISIBLE
        current_menu_state_view.text = stateMessage

        if (error != null) {
            Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun renderSpeechRecognitionState() {
        // TODO: Add a dedicated view
        initialization_view.visibility = View.GONE
        initialization_error_view.visibility = View.GONE
        menu_view.visibility = View.GONE
    }
}
