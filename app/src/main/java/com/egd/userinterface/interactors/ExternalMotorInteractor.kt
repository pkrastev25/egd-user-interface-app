package com.egd.userinterface.interactors

import com.egd.userinterface.buses.ReducerInteractorCommunicationBus
import com.egd.userinterface.buses.events.ExternalMotorToggleEvent
import com.egd.userinterface.interactors.interfaces.IExternalMotorInteractor
import com.egd.userinterface.services.interfaces.IExternalMotorService
import com.egd.userinterface.views.menu.MenuIntent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author Petar Krastev
 */
class ExternalMotorInteractor(
        externalMotorService: IExternalMotorService
) : IExternalMotorInteractor {

    private val mExternalMotorService = externalMotorService

    override fun getExternalMotorInitStateIntent(): Observable<MenuIntent> {
        return mExternalMotorService.getInitState()
                .flatMap<MenuIntent> {
                    Observable.empty()
                }.onErrorReturn {
                    MenuIntent.ExternalDeviceInitErrorIntent(
                            error = it
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getExternalMotorToggleStateIntent(): Observable<MenuIntent> {
        return ReducerInteractorCommunicationBus.listen(ExternalMotorToggleEvent::class.java)
                .switchMap {
                    if (it.shouldStart) {
                        mExternalMotorService.startMotor()
                    } else {
                        mExternalMotorService.stopMotor()
                    }
                }.flatMap<MenuIntent> {
                    Observable.empty()
                }.onErrorReturn {
                    MenuIntent.ExternalDeviceErrorIntent(
                            error = it
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun release() {
        mExternalMotorService.release()
    }
}