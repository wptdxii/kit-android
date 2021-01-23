package com.rocbillow.base.extension

import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.StringRes
import com.rocbillow.base.utils.ContextProvider
import me.drakeet.support.toast.ToastCompat

/**
 * @author rocbillow
 * @date 2021-01-05
 */

val handler: Handler by lazy {
  Handler(Looper.getMainLooper())
}

private var toast: Toast? = null

/**
 * Show a standard toast with string text.
 */
@JvmOverloads
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

@JvmOverloads
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
    toast?.cancel()
    toast = ToastCompat.makeText(ContextProvider.context, charSequence, duration)
    toast?.let {
      if (hideAppName) it.setText(charSequence)
      if (centerShow) {
        it.setGravity(Gravity.CENTER, 0, 0)
      }
      it.show()
    }
  }
}
