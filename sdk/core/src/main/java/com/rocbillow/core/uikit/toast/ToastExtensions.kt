package com.rocbillow.core.uikit.toast

import android.view.Gravity
import android.view.View
import androidx.annotation.StringRes
import com.rocbillow.core.R
import com.rocbillow.core.assist.ContextProvider
import com.rocbillow.core.databinding.CustomToastViewBinding
import com.rocbillow.core.extension.inflate

/**
 * @author rocbillow
 * @date 2021-01-05
 */

fun CharSequence?.toast() = showTextToast(this)

fun @receiver:StringRes Int.toast() = showTextToast(ContextProvider.context.getString(this))

fun CharSequence?.success() {
  val toastView = inflate(R.layout.custom_toast_view)
  val viewBinding = CustomToastViewBinding.bind(toastView)
  viewBinding.ivToast.setImageResource(R.drawable.vector_ic_success)
  viewBinding.tvToast.text = this
  showCustomToast(this, toastView)
}

private fun showCustomToast(text: CharSequence?, view: View) {
  if (!text.isNullOrBlank()) {
    ToastManager.make()
      .view(view)
      .gravity(Gravity.CENTER)
      .show()
  }
}

private fun showTextToast(text: CharSequence?) {
  if (!text.isNullOrBlank()) {
    ToastManager.show {
      this.text = text
    }
  }
}