package com.test.logoquiz.ui.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * This is the Base implementation for the ViewModel that is being used through out the App for MVVM Pattern.
 */
abstract class BaseViewModel: ViewModel() {

    // Scheduler Facade provide the Schedulers to be used for different Rx Subscribers

    // This CompositeDisposable should be used to add Disposables created by different Rx Subscription. Never leave
    // a Disposable Hanging. Always add to composite disposable or dispose it anyway, Cause undisposed Subscriptions
    // might lead to potential leaks
    protected val compositeDisposable = CompositeDisposable()

    /**
     * This method is called from the view when view is created. i.e in onCreate of an Activity, But for Fragments
     * this method is not called automatically, Since Fragments creation can be very Tricky. The developer is free
     * to call this method when it is necessary to act on View Creation
     */

    @CallSuper
    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}