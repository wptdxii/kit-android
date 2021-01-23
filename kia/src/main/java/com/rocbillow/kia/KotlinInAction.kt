package com.rocbillow.kia

inline fun <reified T> foo(noinline block: () -> Unit) {
  // 会被内联
  block()
}