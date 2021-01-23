package com.tahhan.micalculator
import android.app.Application
import com.tahhan.micalculator.model.SharedPrefsManager

class App : Application() {

    companion object {
        lateinit var sharedPrefsManager: SharedPrefsManager
    }

    override fun onCreate() {
        super.onCreate()
        sharedPrefsManager =
            SharedPrefsManager(applicationContext)
    }
}