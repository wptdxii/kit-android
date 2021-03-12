package com.rocbillow.core.extension

import java.util.*
import kotlin.concurrent.schedule

const val DEBOUNCE_DURATION = 300L
var debounceTimer: Timer? = null

inline fun debounce(crossinline action: () -> Unit) {
  debounceTimer?.cancel()
  debounceTimer = Timer().apply {
    schedule(DEBOUNCE_DURATION) {
      action()
      debounceTimer = null
    }
  }
}