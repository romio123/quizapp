package com.test.logoquiz.ui.base

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity: DaggerAppCompatActivity() {

    protected val compositeDisposable = CompositeDisposable()

    // This function should be implemented to return the layout Id for the Activity
    @LayoutRes
    abstract fun getLayoutId(): Int

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        addSubComponent()
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        setupViews()
        initListeners()
    }

    /**
     * This method should be used to setup views. e.g Setting RecyclerView Adapters or
     * showing initial state of Activities
     */
    open fun setupViews() {}

    /**
     * This method should be used to initialise the listeners for a view or multiple
     * views in the activity
     */
    open fun initListeners() {}

    /**
     * Add Subcomponent dependencies to Main App Component. To be used very carefully.
     * Only add those subcomponents to Dagger graph, which are absolutely necessary for the feature.
     */
    open fun addSubComponent() {}

    /**
     * Remove the added Subcomponents from the Dagger Graph
     */
    open fun removeSubComponent() {}

    @CallSuper
    override fun onDestroy() {
        compositeDisposable.clear()
        removeSubComponent()
        super.onDestroy()
    }

    open fun updateStatusBarColor(@ColorRes statusBarColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(applicationContext, statusBarColor)
        }
    }
}