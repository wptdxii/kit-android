package com.rocbillow.core.widget.toast

import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.rocbillow.core.R
import com.rocbillow.core.assist.ContextProvider
import com.rocbillow.core.databinding.CustomToastViewBinding
import com.rocbillow.core.extension.inflate

/**
 * @author rocbillow
 * @date 2021-01-05
 */

fun CharSequence?.toast() = show(this)

fun @receiver:StringRes Int.toast() = show(ContextProvider.context.getString(this))

fun CharSequence?.success() {
  val toastView = inflate(R.layout.custom_toast_view)
  val viewBinding = CustomToastViewBinding.bind(toastView)
  viewBinding.ivToast.setImageResource(R.drawable.vector_ic_success)
  viewBinding.tvToast.text = this
  show(this, toastView)
}

private fun show(text: CharSequence?, view: View? = null) {
  if (text.isNullOrEmpty() || text.isNullOrBlank()) return

  ToastManager.show {
    this.text = text
    this.view = view
    duration = Toast.LENGTH_SHORT
    gravity = Gravity.CENTER
    xOffset = 0
    yOffset = 0
  }
}