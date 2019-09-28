package com.test.logoquiz

import android.os.Build
import android.os.StrictMode
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.BuildCompat
import com.test.logoquiz.BuildConfig.DEBUG
import com.test.logoquiz.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class LogoQuizApplicaton: DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        val nightMode = if (BuildCompat.isAtLeastQ()) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
        initLoggingAndCrashReporting()
        enableStrictMode()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent
            .builder()
            .build()

    private fun initLoggingAndCrashReporting() {
        if(DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun enableStrictMode() {
        if (DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder().detectAll()
                    .penaltyLog()
                    .penaltyFlashScreen()
                    .build())

            val vmPolicyBuilder = StrictMode.VmPolicy.Builder().detectActivityLeaks()
                .detectLeakedClosableObjects()
                .detectLeakedRegistrationObjects()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                vmPolicyBuilder.detectFileUriExposure()
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vmPolicyBuilder.detectContentUriWithoutPermission()
            }
            StrictMode.setVmPolicy(vmPolicyBuilder.build())
        }
    }
}