package ru.shanin.madarareddit.utils.extensions

import android.view.LayoutInflater
import android.view.ViewGroup

fun <T> ViewGroup.bindingInflate(
    inflate: (LayoutInflater, ViewGroup?, Boolean) -> T,
    attachToParent: Boolean = false
): T {
    return inflate(
        LayoutInflater.from(context),
        this,
        attachToParent
    )
}