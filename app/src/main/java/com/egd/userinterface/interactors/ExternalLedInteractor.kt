package com.egd.userinterface.interactors

import com.egd.userinterface.buses.ReducerInteractorCommunicationBus
import com.egd.userinterface.buses.events.ExternalLedToggleEvent
import com.egd.userinterface.interactors.interfaces.IExternalLedInteractor
import com.egd.userinterface.services.interfaces.IExternalLedService
import com.egd.userinterface.views.menu.MenuIntent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author Petar Krastev
 */
class ExternalLedInteractor(
        externalLedService: IExternalLedService
) : IExternalLedInteractor {

    private val mExternalLedService = externalLedService

    override fun getExternalLedInitStateIntent(): Observable<MenuIntent> {
        return mExternalLedService.getInitState()
                .flatMap<MenuIntent> {
                    Observable.empty()
                }.onErrorReturn {
                    MenuIntent.ExternalDeviceInitErrorIntent(
                            error = it
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getExternalLedToggleStateIntent(): Observable<MenuIntent> {
        return ReducerInteractorCommunicationBus.listen(ExternalLedToggleEvent::class.java)
                .switchMap {
                    if (it.shouldTurnOn) {
                        mExternalLedService.turnLedOn()
                    } else {
                        mExternalLedService.turnLedOff()
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
        mExternalLedService.release()
    }
}