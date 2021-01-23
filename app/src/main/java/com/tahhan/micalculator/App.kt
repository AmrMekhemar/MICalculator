package com.tahhan.micalculator
import android.app.Application
import com.tahhan.micalculator.model.SharedPrefsManager

/**
 * AppClass
 */
class App : Application() {

    companion object {
        lateinit var sharedPrefsManager: SharedPrefsManager
    }

    /**
     * creates a sharedPrefsManager object when the app launches
     *
     */
    override fun onCreate() {
        super.onCreate()
        sharedPrefsManager =
            SharedPrefsManager(applicationContext)
    }
}