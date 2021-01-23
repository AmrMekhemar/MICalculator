package com.tahhan.micalculator

import android.content.Context
import android.widget.Toast

/**
 * an extension function to make a toast
 *
 * @param message
 * @param length
 */
fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}
