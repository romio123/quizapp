package com.test.logoquiz.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Component(modules = [
    AppModule::class,
    AndroidSupportInjectionModule::class
])
@Singleton
interface AppComponent: AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }
}