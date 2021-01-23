package com.tahhan.micalculator.model

import android.content.Context

private const val SHARED_PREFS = "1001"
private const val RESULT_KEY = "1002"

class SharedPrefsManager(private val context: Context) {
     var firstOperand = 0.0F
        get() = sharedPrefs().getFloat(RESULT_KEY, 0F)
        set(value) {
            sharedPrefs().edit().putFloat(RESULT_KEY, value).apply()
            field = value
        }

    private fun sharedPrefs() =
        context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
}