package com.olaroc.core.uikit

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

sealed interface State {
    object IDLE : State
    object EXPANDED : State
    object COLLAPSED : State
}

fun interface StateChangedListener {
    fun onStateChanged(appBarLayout: AppBarLayout, state: State)
}

inline fun AppBarLayout.addOnCollapsedListener(crossinline action: () -> Unit) {
    addOnOffsetChangedListener(AppbarLayoutOffsetChangedListener { _, state ->
        if (state == State.COLLAPSED) {
            action()
        }
    })
}

class AppbarLayoutOffsetChangedListener(val listener: StateChangedListener) :
    AppBarLayout.OnOffsetChangedListener {

    private var state: State = State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (verticalOffset == 0) {
            if (state != State.IDLE) {
                listener.onStateChanged(appBarLayout, State.EXPANDED)
            }
            state = State.EXPANDED
            return
        }

        if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
            if (state != State.COLLAPSED) {
                listener.onStateChanged(appBarLayout, State.COLLAPSED)
            }
            state = State.COLLAPSED
            return
        }
        if (state != State.IDLE) {
            listener.onStateChanged(appBarLayout, State.IDLE)
        }
        state = State.IDLE
    }
}
