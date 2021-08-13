package com.olaroc.core.systembar

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout

/**
 * Dispatch WindowInsets when root layout is CoordinatorLayout
 */
fun CoordinatorLayout.dispatchWindowInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        insets
    }
}

fun DrawerLayout.dispatchWindowInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        insets
    }
}
