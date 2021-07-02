package com.rocbillow.core.uikit.toast

import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.annotation.IntDef
import com.rocbillow.core.extension.runOnUiThread
import java.lang.ref.WeakReference


const val TOAST_LENGTH_SHORT = 0

const val TOAST_LENGTH_LONG = 1

@IntDef(TOAST_LENGTH_SHORT, TOAST_LENGTH_LONG)
@Retention(AnnotationRetention.SOURCE)
annotation class Duration

private var toastStrategyWeakRef: WeakReference<ToastStrategy>? = null

class ToastManager private constructor(private val builder: Builder) {

  companion object {

    @JvmStatic
    fun show(text: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
      show {
        this.text = text
        this.duration = duration
      }
    }

    @JvmStatic
    inline fun show(cancelPreviousToast: Boolean = true, block: Builder.() -> Unit) =
      Builder().apply(block).build().show(cancelPreviousToast)

    @JvmStatic
    fun make() = Builder()
  }

  fun show(cancelPreviousToast: Boolean = true) {
    runOnUiThread {
      if (cancelPreviousToast) {
        toastStrategyWeakRef?.get()?.cancel()
      }
      val toast = builder.toastStrategyFactory.create()
      toastStrategyWeakRef = WeakReference(toast)
      toast.show()
    }
  }

  class Builder {
    var text: CharSequence? = null
    var view: View? = null
    var gravity: Int = Gravity.NO_GRAVITY
    var xOffset = 0
    var yOffset = 0
    var darkMode = false
    var toastStrategyFactory: ToastStrategyFactory = ToastStrategyFactoryImpl(this)

    fun text(text: CharSequence?): Builder {
      this.text = text
      return this
    }

    fun view(view: View?): Builder {
      this.view = view
      return this
    }

    fun gravity(gravity: Int): Builder {
      this.gravity = gravity
      return this
    }

    fun xOffset(xOffset: Int): Builder {
      this.xOffset = xOffset
      return this
    }

    fun yOffset(yOffset: Int): Builder {
      this.yOffset = yOffset
      return this
    }

    fun darkMode(darkMode: Boolean): Builder {
      this.darkMode = darkMode
      return this
    }

    fun toastStrategy(strategyFactory: Builder.() -> ToastStrategyFactory): Builder {
      this.toastStrategyFactory = strategyFactory()
      return this
    }

    @Duration
    var duration: Int = TOAST_LENGTH_SHORT

    fun build() = ToastManager(this)

    fun show(cancelPreviousToast: Boolean = true) = build().show(cancelPreviousToast)
  }
}