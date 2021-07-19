package com.olaroc.core.uikit.toast

import android.content.Context
import android.graphics.PixelFormat
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.DrawableCompat
import com.olaroc.core.R
import com.olaroc.core.assist.ActivityManager
import com.olaroc.core.assist.AppManager
import com.olaroc.core.assist.ContextProvider
import com.olaroc.core.databinding.ToastViewBinding
import com.olaroc.core.extension.inflate
import com.olaroc.core.extension.runOnUiThread
import com.olaroc.core.extension.screenHeight
import com.olaroc.core.uikit.toast.compat.ToastCompat

interface ToastStrategy {

  fun show()

  fun cancel()
}

internal class SystemToastStrategy(private val builder: ToastManager.Builder) : ToastStrategy {

  private var toast: Toast =
    ToastCompat.makeText(ContextProvider.context, builder.text, builder.duration)

  override fun show() = with(toast) {
    setText(builder.text)
    setView()
    setGravity()
    show()
  }

  override fun cancel() {
    toast.cancel()
  }

  @Suppress("DEPRECATION")
  private fun Toast.setView() {
    if (isCustomViewEnable()) {
      view = builder.view
      return
    }
    setDefaultToastView()
  }

  @Suppress("DEPRECATION")
  private fun Toast.setDefaultToastView() {
    val toastView = view
    toastView?.let {
      val background = it.background
      val colorParser = ToastStyle(builder.darkMode)
      val backgroundColor = colorParser.getBackgroundColor()
      if (background is GradientDrawable) {
        background.setColor(backgroundColor)
      } else {
        DrawableCompat.setTint(background, backgroundColor)
      }

      val toastTextView: TextView? = toastView.findViewById(android.R.id.message)
      toastTextView?.setTextColor(colorParser.getTextColor())
    }
  }

  private fun isCustomViewEnable(): Boolean = isSdkLessThan30() and (builder.view != null)

  private fun isSdkLessThan30() = Build.VERSION.SDK_INT < Build.VERSION_CODES.R

  private fun Toast.setGravity() {
    if ((builder.gravity != Gravity.NO_GRAVITY) and isSdkLessThan30()) {
      with(builder) {
        setGravity(gravity, xOffset, yOffset)
      }
    }
  }
}

private const val DEFAULT_Y_OFFSET = 72

internal class WindowManagerToastStrategy(
  private val builder: ToastManager.Builder,
  private var windowManager: WindowManager,
  private val toastType: Int,
) : ToastStrategy {

  private val layoutParams = WindowManager.LayoutParams()

  constructor(builder: ToastManager.Builder, toastType: Int) : this(
    builder,
    ContextProvider.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager,
    toastType
  )

  override fun show() {
    setLayoutParams()
    addView()
    autoCancel()
  }

  override fun cancel() {
    try {
      windowManager.removeViewImmediate(builder.view)
    } catch (ignored: Exception) {
      // ignored
    }
  }

  private fun setLayoutParams() = with(layoutParams) {
    type = toastType
    height = WindowManager.LayoutParams.WRAP_CONTENT
    width = WindowManager.LayoutParams.WRAP_CONTENT
    format = PixelFormat.TRANSLUCENT
    windowAnimations = android.R.style.Animation_Toast
    title = "ToastWithoutNotification"
    flags = (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    windowAnimations = android.R.style.Animation_Toast
    packageName = ContextProvider.context.packageName

    val gravity = builder.gravity
    if (gravity == Gravity.NO_GRAVITY) {
      builder.gravity = Gravity.BOTTOM
      builder.yOffset = DEFAULT_Y_OFFSET
    } else if (gravity == Gravity.CENTER) {
      // Gravity.CENTER has problems when toast in onCreate(), use Gravity.BOTTOM instead.
      builder.gravity = Gravity.BOTTOM
      val toastViewHeight = builder.view?.height ?: 0
      builder.yOffset = (screenHeight - toastViewHeight) / 2 + builder.yOffset
    }
    this.gravity = builder.gravity
    x = builder.xOffset
    y = builder.yOffset

    if (gravity and Gravity.HORIZONTAL_GRAVITY_MASK == Gravity.FILL_HORIZONTAL) {
      horizontalWeight = 1.0f
    }
    if (gravity and Gravity.VERTICAL_GRAVITY_MASK == Gravity.FILL_VERTICAL) {
      verticalWeight = 1.0f
    }
  }

  private fun addView() {
    try {
      var view = builder.view
      if (view == null) {
        val toastStyle = ToastStyle(builder.darkMode)
        view = inflate(R.layout.toast_view)
        val viewBinding = ToastViewBinding.bind(view)
        viewBinding.root.background = toastStyle.getBackgroundDrawable()
        viewBinding.tvMessage.apply {
          text = builder.text
          setTextColor(toastStyle.getTextColor())
        }
        builder.view = view
      }
      windowManager.addView(view, layoutParams)
    } catch (ignored: Exception) {
      // ignored
    }
  }

  private fun autoCancel() {
    val delayMillis: Long = if (builder.duration == Toast.LENGTH_SHORT) 2000 else 3000
    runOnUiThread(delayMillis) {
      cancel()
    }
  }
}

internal class ActivityToastStrategy(private val builder: ToastManager.Builder) : ToastStrategy {

  private lateinit var toastStrategy: ToastStrategy

  override fun show() {
    toastStrategy = createToastStrategy()
    toastStrategy.show()
  }

  override fun cancel() {
    toastStrategy.cancel()
  }

  private fun createToastStrategy(): ToastStrategy = when {
    isAppBackground() -> SystemToastStrategy(builder)
    hasActiveActivity() -> createWindowManagerStrategy()
    else -> SystemToastStrategy(builder)
  }

  private fun isAppBackground() = !AppManager.isForeground

  private fun createWindowManagerStrategy(): ToastStrategy {
    val currentActivity = ActivityManager.currentActivity()!!
    return WindowManagerToastStrategy(
      builder,
      currentActivity.windowManager,
      WindowManager.LayoutParams.LAST_APPLICATION_WINDOW
    )
  }

  private fun hasActiveActivity(): Boolean {
    val currentActivity = ActivityManager.currentActivity()
    return currentActivity != null && !currentActivity.isFinishing
  }
}
