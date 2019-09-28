package com.test.logoquiz.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.logoquiz.ui.activities.QuizActivity
import com.test.logoquiz.ui.viewmodels.QuizViewModel
import com.test.logoquiz.ui.viewmodels.di.ViewModelFactory
import com.test.logoquiz.utils.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class AppUiModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector
    abstract fun bindQuizActivity(): QuizActivity


    @Binds
    @IntoMap
    @ViewModelKey(QuizViewModel::class)
    abstract fun bindQuizViewModel(quizViewModel: QuizViewModel): ViewModel
}