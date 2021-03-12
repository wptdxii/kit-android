package com.rocbillow.core.extension

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * @author rocbillow
 * @date 2020-12-04
 */

inline fun <reified T : AppCompatActivity> Context.start() {
  val intent = Intent(this, T::class.java)
  start(intent)
}

inline fun <reified T : AppCompatActivity> Context.start(block: Intent.() -> Unit) {
  val intent = Intent(this, T::class.java).apply { block() }
  start(intent)
}

inline fun <reified T : AppCompatActivity> Fragment.start() {
  requireContext().start<T>()
}

inline fun <reified T : AppCompatActivity> Fragment.start(block: Intent.() -> Unit) {
  requireContext().start<T>(block)
}

fun Fragment.start(
  clazz: Class<out AppCompatActivity>,
  block: (Intent.() -> Unit)? = null,
) {
  requireContext().start(clazz, block)
}

fun Context.start(clazz: Class<out AppCompatActivity>, block: (Intent.() -> Unit)?) {
  val intent = Intent(this, clazz)
  block?.let { intent.it() }
  start(intent)
}

fun Context.start(intent: Intent) {
  if (this !is AppCompatActivity) {
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
  }
  startActivity(intent)
}