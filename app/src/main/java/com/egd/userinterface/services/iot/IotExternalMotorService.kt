package com.egd.userinterface.services.iot

import android.content.Context
import com.egd.userinterface.R
import com.egd.userinterface.services.interfaces.IExternalMotorService
import com.egd.userinterface.services.interfaces.ITextToSpeechService
import com.egd.userinterface.services.interfaces.iot.IIotOutputPwmService
import io.reactivex.Observable

/**
 * @author Petar Krastev
 */
class IotExternalMotorService(
        context: Context,
        externalMotor: IIotOutputPwmService,
        textToSpeechService: ITextToSpeechService
) : IExternalMotorService {

    private val mContext = context
    private val mExternalMotor = externalMotor
    private val mTextToSpeechService = textToSpeechService

    override fun getInitState(): Observable<Unit> {
        return mExternalMotor.getInitState()
    }

    override fun startMotor(): Observable<Unit> {
        return mExternalMotor.enabledPwmOutput()
                .switchMap {
                    mTextToSpeechService.convertTextToSpeech(
                            mContext.getString(R.string.external_motor_feedback_started)
                    )
                }
    }

    override fun stopMotor(): Observable<Unit> {
        return mExternalMotor.disablePwmOutput()
                .switchMap {
                    mTextToSpeechService.convertTextToSpeech(
                            mContext.getString(R.string.external_motor_feedback_stopped)
                    )
                }
    }

    override fun release() {
        mExternalMotor.release()
    }
}