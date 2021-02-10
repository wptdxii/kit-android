package com.rocbillow.base.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


fun <T, R> LiveData<T>.mapAsync(
  dispatcher: CoroutineDispatcher = Dispatchers.IO,
  mapper: suspend (T) -> R,
): LiveData<R> = switchMap { value ->
  liveData {
    withContext(dispatcher) {
      emit(mapper(value))
    }
  }
}
