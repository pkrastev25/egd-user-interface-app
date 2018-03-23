package com.egd.userinterface.services.iot

import android.content.Context
import com.egd.userinterface.R
import com.egd.userinterface.services.interfaces.IExternalLedService
import com.egd.userinterface.services.interfaces.ITextToSpeechService
import com.egd.userinterface.services.interfaces.iot.IIotOutputGpioService
import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
class IotExternalLedService(
        context: Context,
        externalLed: IIotOutputGpioService,
        textToSpeechService: ITextToSpeechService
) : IExternalLedService {

    private val mContext = context.applicationContext
    private val mExternalLed = externalLed
    private val mTextToSpeechService = textToSpeechService

    override fun getInitState(): Observable<Unit> {
        return mExternalLed.getInitState()
    }

    override fun turnLedOn(): Observable<Unit> {
        return mExternalLed.enableGpioOutput()
                .switchMap {
                    mTextToSpeechService.convertTextToSpeech(
                            mContext.getString(R.string.external_led_feedback_on)
                    )
                }
    }

    override fun turnLedOff(): Observable<Unit> {
        return mExternalLed.disableGpioOutput()
                .switchMap {
                    mTextToSpeechService.convertTextToSpeech(
                            mContext.getString(R.string.external_led_feedback_off)
                    )
                }
    }

    override fun release() {
        mExternalLed.release()
    }
}