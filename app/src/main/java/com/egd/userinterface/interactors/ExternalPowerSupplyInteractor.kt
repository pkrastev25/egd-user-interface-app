package com.egd.userinterface.interactors

import com.egd.userinterface.interactors.interfaces.IExternalPowerSupplyInteractor
import com.egd.userinterface.services.interfaces.IExternalPowerSupplyService
import com.egd.userinterface.views.menu.MenuIntent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author Petar Krastev
 */
class ExternalPowerSupplyInteractor(
        externalPowerSupplyService: IExternalPowerSupplyService
) : IExternalPowerSupplyInteractor {

    private val mExternalPowerSupplyService = externalPowerSupplyService

    override fun getExternalPowerSupplyInitState(): Observable<MenuIntent> {
        return mExternalPowerSupplyService.getInitState()
                .flatMap<MenuIntent> {
                    Observable.empty()
                }.onErrorReturn {
                    MenuIntent.ExternalDeviceInitErrorIntent(
                            error = it
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun release() {
        mExternalPowerSupplyService.release()
    }
}