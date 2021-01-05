package com.rocbillow.base.utils

import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.StringRes
import me.drakeet.support.toast.ToastCompat

/**
 * @author rocbillow
 * @date 2021-01-05
 */

val handler: Handler by lazy {
  Handler(Looper.getMainLooper())
}

lateinit var toast: Toast

/**
 * Show a standard toast with string text.
 */
fun CharSequence?.toast(
  duration: Int = Toast.LENGTH_SHORT,
  centerShow: Boolean = false,
  hideAppName: Boolean = true,
) {
  show(this, duration, centerShow, hideAppName)
}

/**
 * Show a standard toast with string res text.
 * Resources.NotFoundException if the resource can't be found.
 *
 * Notice that, If the receiver is not string resource, Android Studio will not lint the error.
 * @see <a href= "https://discuss.kotlinlang.org/t/receiver-stringres-android-studio-lint-invalid/19066">Android Studio lint Invalid</a>
 * @see <a href="https://stackoverflow.com/questions/51156576/kotlin-annotate-receiver-of-extension-function">Kotlin annotate receiver of extension function</a>
 *
 */
fun @receiver:StringRes Int.toast(
  duration: Int = Toast.LENGTH_SHORT,
  centerShow: Boolean = false,
  hideAppName: Boolean = true,
) {
  show(ContextProvider.context.getString(this), duration, centerShow, hideAppName)
}

private fun show(
  charSequence: CharSequence?,
  duration: Int,
  centerShow: Boolean = false,
  hideAppName: Boolean = true,
) {
  if (charSequence.isNullOrEmpty() || charSequence.isNullOrBlank()) return
  handler.post {
    if (::toast.isInitialized) toast.cancel()
    toast = ToastCompat.makeText(ContextProvider.context, charSequence, duration)
    if (hideAppName) toast.setText(charSequence)
    toast.setGravity(if (centerShow) Gravity.CENTER else Gravity.BOTTOM, 0, 0)
    toast.show()
  }
}
