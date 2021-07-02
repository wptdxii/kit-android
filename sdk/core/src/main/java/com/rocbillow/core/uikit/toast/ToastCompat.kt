package com.rocbillow.core.uikit.toast.compat

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes

internal class ToastCompat(context: Context, private val base: Toast) : Toast(context) {

  companion object {
    @JvmStatic
    @SuppressLint("ShowToast")
    @Suppress("DEPRECATION")
    fun makeText(context: Context, text: CharSequence?, duration: Int): ToastCompat {
      val toast = Toast.makeText(context, text, duration)
      setContextCompat(toast.view, ToastContextWrapper(context, toast))
      return ToastCompat(context, toast)
    }

    @JvmStatic
    @SuppressLint("ShowToast")
    @Suppress("DEPRECATION")
    fun makeText(context: Context, @StringRes resId: Int, duration: Int): ToastCompat {
      val toast = Toast.makeText(context, context.resources.getString(resId), duration)
      setContextCompat(toast.view, ToastContextWrapper(context, toast))
      return ToastCompat(context, toast)
    }

    @JvmStatic
    private fun setContextCompat(view: View?, context: ToastContextWrapper) {
      if (Build.VERSION.SDK_INT != Build.VERSION_CODES.N_MR1 || view == null) return
      try {
        val field = View::class.java.getDeclaredField("mContext")
        field.isAccessible = true
        field[view] = context
      } catch (ignored: Throwable) {
      }
    }
  }

  fun catchBadToken(badTokenListener: BadTokenListener): ToastCompat {
    view?.let {
      val context = it.context
      if (context is ToastContextWrapper) {
        context.catchBadToken(badTokenListener)
      }
    }
    return this
  }

  override fun show() = base.show()

  override fun setDuration(duration: Int) {
    base.duration = duration
  }

  override fun setGravity(gravity: Int, xOffset: Int, yOffset: Int) =
    base.setGravity(gravity, xOffset, yOffset)

  override fun setMargin(horizontalMargin: Float, verticalMargin: Float) =
    base.setMargin(horizontalMargin, verticalMargin)

  override fun setText(s: CharSequence?) = base.setText(s)

  override fun setText(resId: Int) = base.setText(resId)

  override fun setView(view: View?) {
    @Suppress("DEPRECATION")
    base.view = view
    view?.let {
      setContextCompat(view, ToastContextWrapper(it.context, base))
    }
  }

  override fun getHorizontalMargin(): Float = base.horizontalMargin

  override fun getVerticalMargin(): Float = base.verticalMargin

  override fun getDuration(): Int = base.duration

  override fun getGravity(): Int = base.gravity

  override fun getXOffset(): Int = base.xOffset

  override fun getYOffset(): Int = base.yOffset

  @Suppress("DEPRECATION")
  override fun getView(): View? = base.view

  override fun cancel() = base.cancel()
}

internal typealias BadTokenListener = (Toast) -> Unit

internal class ToastContextWrapper(base: Context, private val toast: Toast) : ContextWrapper(base) {

  private var badTokenListener: BadTokenListener? = null

  fun catchBadToken(badTokenListener: BadTokenListener) {
    this.badTokenListener = badTokenListener
  }

  override fun getApplicationContext(): Context =
    ApplicationContextWrapper(baseContext.applicationContext)

  private inner class ApplicationContextWrapper(base: Context) : ContextWrapper(base) {
    override fun getSystemService(name: String): Any = when (name) {
      Context.WINDOW_SERVICE -> WindowManagerWrapper(baseContext.getSystemService(name) as WindowManager)
      else -> super.getSystemService(name)
    }
  }

  private inner class WindowManagerWrapper(
    private val base: WindowManager,
  ) : WindowManager by base {
    override fun addView(view: View?, params: ViewGroup.LayoutParams?) {
      try {
        base.addView(view, params)
      } catch (ignored: WindowManager.BadTokenException) {
        badTokenListener?.invoke(toast)
      }
    }
  }
}