package com.rocbillow.kia

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.runBlocking


fun main() = runBlocking<Unit> {
  val initialValue = 0
  val sharedFlow = MutableSharedFlow<Int>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
  sharedFlow.tryEmit(initialValue)
  val stateFlow = sharedFlow.distinctUntilChanged()
}