package com.rocbillow.kia

fun process(value: Int, f: (Int) -> Int) {
  println(f(value))
}
