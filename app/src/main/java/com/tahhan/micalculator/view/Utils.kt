package com.tahhan.micalculator.view

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.tahhan.micalculator.App.Companion.context
import com.tahhan.micalculator.R

/**
 * an extension function to make a toast
 *
 * @param message
 * @param length
 */
fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

private val primaryColor by lazy {
    ContextCompat.getColor(context, R.color.purple_700)
}

/**
 * Makes buttons clickable
 *
 * @param button1
 * @param button2
 * @param button3
 * @param button4
 * @param enterNumberEditText
 */

fun View.makeButtonsClickable(
    button1: Button,
    button2: Button, button3: Button, button4: Button, enterNumberEditText: EditText
) {
    button1.isClickable = true
    button2.isClickable = true
    button3.isClickable = true
    button4.isClickable = true
    button1.setBackgroundColor(primaryColor)
    button2.setBackgroundColor(primaryColor)
    button3.setBackgroundColor(primaryColor)
    button4.setBackgroundColor(primaryColor)
    enterNumberEditText.text?.clear()
}

/**
 * Make buttons non clickable depending on the operation
 *
 * @param operation
 * @param button1
 * @param button2
 * @param button3
 */
fun View.makeButtonsNonClickable(
    operation: String,button1: Button,
    button2: Button, button3: Button
) {
    when (operation) {
        "+" -> {
            setAsNonClickable(button1,button2,button3)
        }
        "-" -> {
            setAsNonClickable(button1,button2,button3)
        }
        "/" -> {
            setAsNonClickable(button1,button2,button3)
        }
        "*" -> {
            setAsNonClickable(button1,button2,button3)
        }
    }

}

private fun setAsNonClickable(button1: Button,
                              button2: Button,  button3: Button) {
    button1.isClickable = false
    button2.isClickable = false
    button3.isClickable = false
    button1.setBackgroundColor(Color.GRAY)
    button2.setBackgroundColor(Color.GRAY)
    button3.setBackgroundColor(Color.GRAY)
}


