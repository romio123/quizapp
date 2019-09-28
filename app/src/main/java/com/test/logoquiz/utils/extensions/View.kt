package com.test.logoquiz.utils.extensions

import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

fun View.throttleClick(duration: Long = 500, block: () -> Unit): Disposable {
    return this.clicks()
        .throttleFirst(duration, TimeUnit.MILLISECONDS)
        .subscribe({
            block()
        }, {})
}