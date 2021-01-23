package com.tahhan.micalculator
import android.app.Application
import android.content.Context
import com.tahhan.micalculator.model.SharedPrefsManager

/**
 * AppClass
 */
class App : Application() {

    companion object {
        lateinit var sharedPrefsManager: SharedPrefsManager
        lateinit var context: Context
    }

    /**
     * creates a sharedPrefsManager object when the app launches
     *
     */
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        sharedPrefsManager =
            SharedPrefsManager(context)
    }
}