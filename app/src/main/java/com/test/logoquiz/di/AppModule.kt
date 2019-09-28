package com.test.logoquiz.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.test.logoquiz.data.di.AppDataModule
import com.test.logoquiz.ui.di.AppUiModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named


@Module(
    includes = [
        AppUiModule::class,
        AppDataModule::class
    ]
)
abstract class AppModule {
    @Binds
    abstract fun bindContext(application: Application): Context

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideGson(): Gson = Gson()

        @JvmStatic
        @Named("score_factor")
        @Provides
        fun provideScoreCaclculationFactor(): Long = 3

    }
}