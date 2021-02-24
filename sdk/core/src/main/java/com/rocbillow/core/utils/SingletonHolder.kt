package com.rocbillow.core.utils

abstract class SingletonHolder<out T, in A>(private val creator: (A) -> T) {

  @Volatile
  private var instance: T? = null

  fun getInstance(a: A): T = instance ?: synchronized(this) {
    instance ?: creator(a).also { instance = it }
  }
}