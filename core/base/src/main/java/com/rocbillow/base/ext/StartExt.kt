package com.rocbillow.base.ext

import android.content.Context
import android.content.Intent
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * @author rocbillow
 * @date 2020-12-04
 */

fun Context.start(@NonNull clazz: Class<out AppCompatActivity>) {
  val intent = Intent(this, clazz)
  if (this !is AppCompatActivity) {
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
  }
  startActivity(intent)
}

fun Fragment.start(@NonNull clazz: Class<out AppCompatActivity>) {
  requireContext().start(clazz)
}
