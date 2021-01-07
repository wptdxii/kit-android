package com.rocbillow.kia

import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


val readOnlyProvider = PropertyDelegateProvider { _: Any?, property ->
  println("property:${property.name}")
  ReadOnlyProperty { _: Any?, _ -> 42 }
}

//private val readWriteProvider =
val readWriteProvider: PropertyDelegateProvider<Any?, ReadWriteProperty<Any?, Int>> =
  PropertyDelegateProvider { _: Any?, property ->
    println("property:${property.name}")
    object : ReadWriteProperty<Any?, Int> {

      var delegate = 0

      override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        delegate = value
      }

      override fun getValue(thisRef: Any?, property: KProperty<*>): Int = delegate
    }
  }

fun main() {
  val readOnlyDelegate: Int by readOnlyProvider
  var readWriteDelegate: Int by readWriteProvider

  val run = Runnable { println() }
}
