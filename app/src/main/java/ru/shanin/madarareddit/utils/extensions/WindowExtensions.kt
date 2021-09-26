package ru.shanin.madarareddit.utils.extensions

import android.view.Window
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun Window.hideKeyboard() {
    this.currentFocus?.let { view ->
        view.clearFocus()

        val windowInsetsController = WindowInsetsControllerCompat(this, view)

        val ime = WindowInsetsCompat.Type.ime()
        windowInsetsController.hide(ime)
    }
}
