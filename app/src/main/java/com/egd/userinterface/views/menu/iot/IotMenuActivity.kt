package com.egd.userinterface.views.menu.iot

import android.os.Bundle
import com.egd.userinterface.R
import com.egd.userinterface.constants.Constants
import com.egd.userinterface.constants.enums.iot.IotInputGpioEdgeTriggerTypesEnum
import com.egd.userinterface.constants.iot.IotConstants
import com.egd.userinterface.interactors.ExternalLedInteractor
import com.egd.userinterface.interactors.ExternalMotorInteractor
import com.egd.userinterface.interactors.ExternalPowerSupplyInteractor
import com.egd.userinterface.services.TextToSpeechService
import com.egd.userinterface.services.iot.*
import com.egd.userinterface.views.menu.MenuPresenter
import com.egd.userinterface.views.menu.MenuViewState
import com.egd.userinterface.views.menu.interfaces.IMenuActivity
import com.hannesdorfmann.mosby3.mvi.MviActivity
import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
class IotMenuActivity : MviActivity<IMenuActivity, MenuPresenter>(), IMenuActivity {

    // TODO: Create using DI
    private val mPreviousMenuButton = IotInputGpioService(
            applicationContext,
            IotConstants.MENU_PREVIOUS_BUTTON_GPIO_INPUT,
            true,
            IotInputGpioEdgeTriggerTypesEnum.EDGE_RISING
    )
    private val mNextMenuButton = IotInputGpioService(
            applicationContext,
            IotConstants.MENU_NEXT_BUTTON_GPIO_INPUT,
            true,
            IotInputGpioEdgeTriggerTypesEnum.EDGE_RISING
    )
    private val mConfirmMenuButton = IotInputGpioService(
            applicationContext,
            IotConstants.MENU_CONFIRM_BUTTON_GPIO_INPUT,
            true,
            IotInputGpioEdgeTriggerTypesEnum.EDGE_RISING
    )
    private val mBackMenuButton = IotInputGpioService(
            applicationContext,
            IotConstants.MENU_BACK_BUTTON_GPIO_INPUT,
            true,
            IotInputGpioEdgeTriggerTypesEnum.EDGE_RISING
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iot_menu)
    }

    override fun onDestroy() {
        super.onDestroy()

        mPreviousMenuButton.release()
        mNextMenuButton.release()
        mConfirmMenuButton.release()
        mBackMenuButton.release()
    }

    override fun menuButtonInitStateIntent(): Observable<Unit> {
        return Observable.mergeArray(
                mPreviousMenuButton.getInitState(),
                mNextMenuButton.getInitState(),
                mConfirmMenuButton.getInitState(),
                mBackMenuButton.getInitState()
        )
    }

    override fun createPresenter(): MenuPresenter {
        // TODO: Create using DI
        val textToSpeechService = TextToSpeechService(
                applicationContext,
                Constants.TEXT_TO_SPEECH_DEFAULT_LANGUAGE,
                Constants.TEXT_TO_SPEECH_DEFAULT_PITCH,
                Constants.TEXT_TO_SPEECH_DEFAULT_SPEECH_RATE
        )

        return MenuPresenter(
                context = applicationContext,
                textToSpeechService = textToSpeechService,
                externalLedInteractor = ExternalLedInteractor(
                        IotExternalLedService(
                                context = applicationContext,
                                externalLed = IotOutputGpioService(
                                        context = applicationContext,
                                        pinName = IotConstants.EXTERNAL_LED_GPIO_OUTPUT,
                                        isInitiallyHigh = false,
                                        isHighActiveType = true
                                ),
                                textToSpeechService = textToSpeechService
                        )
                ),
                externalMotorInteractor = ExternalMotorInteractor(
                        IotExternalMotorService(
                                context = applicationContext,
                                externalMotor = IotOutputPwmService(
                                        context = applicationContext,
                                        pinName = IotConstants.EXTERNAL_MOTOR_PWM_OUTPUT,
                                        dutyCycle = IotConstants.EXTERNAL_MOTOR_PWM_OUTPUT_DUTY_CYCLE,
                                        frequency = IotConstants.EXTERNAL_MOTOR_PWM_OUTPUT_FREQUENCY
                                ),
                                textToSpeechService = textToSpeechService

                        )
                ),
                externalPowerSupplyInteractor = ExternalPowerSupplyInteractor(
                        IotExternalPowerSupplyService(
                                externalPowerSupply = IotOutputGpioService(
                                        context = applicationContext,
                                        pinName = IotConstants.EXTERNAL_POWER_SUPPLY_GPIO_OUTPUT,
                                        isInitiallyHigh = true,
                                        isHighActiveType = true
                                )
                        )
                )
        )
    }

    override fun previousMenuButtonIntent(): Observable<Unit> {
        return mPreviousMenuButton.getInputIntent()
    }

    override fun nextMenuButtonIntent(): Observable<Unit> {
        return mNextMenuButton.getInputIntent()
    }

    override fun confirmMenuButtonIntent(): Observable<Unit> {
        return mConfirmMenuButton.getInputIntent()
    }

    override fun backMenuButtonIntent(): Observable<Unit> {
        return mBackMenuButton.getInputIntent()
    }

    override fun render(state: MenuViewState) {
        // Always show the same view here
    }
}
