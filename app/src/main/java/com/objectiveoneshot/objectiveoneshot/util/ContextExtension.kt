package com.objectiveoneshot.objectiveoneshot.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showKeyboard(view: View, isShown: Boolean = true) {
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).run {
        if (isShown) showSoftInput(view, 0)
        else hideSoftInputFromWindow(view.windowToken, 0)
    }
}