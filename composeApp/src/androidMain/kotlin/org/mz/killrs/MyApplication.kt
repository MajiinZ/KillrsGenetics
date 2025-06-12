package org.mz.killrs

import android.app.Application
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import org.killrs.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin (
            config = {
                androidContext(this@MyApplication)
            }
        )
        Firebase.initialize(context = this)
    }
}