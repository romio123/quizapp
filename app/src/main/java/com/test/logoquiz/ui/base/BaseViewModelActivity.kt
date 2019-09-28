package com.test.logoquiz.ui.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * This is Base Activity implementation for MVVM pattern.
 * @param viewModelClass
 *
 */
abstract class BaseViewModelActivity<out VM: BaseViewModel>(private val viewModelClass: KClass<VM>): BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val viewModel: VM by lazy {
        ViewModelProvider(viewModelStore, viewModelFactory)[viewModelClass.java]
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
    }

    /**
     * The viewmodel's LiveData should be observed by overriding this method in the
     * inheriting classes
     */
    open fun observeViewModel() {}
}