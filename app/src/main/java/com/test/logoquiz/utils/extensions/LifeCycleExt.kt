package com.test.logoquiz.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.test.logoquiz.utils.exceptions.Failure

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <L : LiveData<Failure>> LifecycleOwner.failure(liveData: L, body: (Failure?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <T> LiveData<T>.reObserve(owner: LifecycleOwner, observer: Observer<T>){
    removeObservers(owner)
    observe(owner, observer)
}