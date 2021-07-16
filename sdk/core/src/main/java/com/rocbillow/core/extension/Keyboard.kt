package com.rocbillow.core.extension

import android.content.Context
import android.os.Bundle
import android.os.ResultReceiver
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.rocbillow.core.assist.ContextProvider

fun showSoftInput() {
    val inputMethodManager = ContextProvider.context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputMethodManager?.toggleSoftInput(
        InputMethodManager.SHOW_FORCED,
        InputMethodManager.HIDE_IMPLICIT_ONLY
    )
}

fun toggleSoftInput() {
    val inputMethodManager = ContextProvider.context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputMethodManager?.toggleSoftInput(0, 0)
}

fun View.showSoftInput() {
    val inputMethodManager = ContextProvider.context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputMethodManager?.let { manager ->
        isFocusable = true
        isFocusableInTouchMode = true
        requestFocus()
        val resultReceiver = object : ResultReceiver(handler) {
            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                if ((resultCode == InputMethodManager.RESULT_UNCHANGED_HIDDEN)
                    or (resultCode == InputMethodManager.RESULT_HIDDEN)
                ) {
                    toggleSoftInput()
                }
            }
        }
        manager.showSoftInput(this, 0, resultReceiver)
        manager.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }
}

fun View.hideSoftInput() {
    val inputMethodManager = ContextProvider.context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
}