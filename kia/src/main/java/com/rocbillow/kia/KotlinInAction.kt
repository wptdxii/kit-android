package com.rocbillow.kia

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
  val job = Job()
  val scope = CoroutineScope(job)

  val j = scope.launch {

  }
}
