package com.egd.userinterface.buses

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Based on https://android.jlelse.eu/super-simple-event-bus-with-rxjava-and-kotlin-f1f969b21003.
 *
 * @author Petar Krastev
 */
object ReducerInteractorCommunicationBus {

    private val mPublisher = PublishSubject.create<Any>()

    fun publish(event: Any) {
        mPublisher.onNext(event)
    }

    fun <T> listen(eventType: Class<T>): Observable<T> {
        return mPublisher.ofType<T>(eventType)
    }
}