package com.egd.userinterface.event.bus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by User on 23.2.2018 г..
 *
 * Based on https://android.jlelse.eu/super-simple-event-bus-with-rxjava-and-kotlin-f1f969b21003.
 */
object EventBus {

    private val mPublisher = PublishSubject.create<Any>()

    fun publish(event: Any) {
        mPublisher.onNext(event)
    }

    fun <T> listen(eventType: Class<T>): Observable<T> {
        return mPublisher.ofType<T>(eventType)
    }
}