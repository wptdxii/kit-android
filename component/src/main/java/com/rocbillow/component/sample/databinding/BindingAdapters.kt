package com.rocbillow.component.sample.databinding

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.rocbillow.component.R
import com.rocbillow.core.extension.hideSoftInput
import com.rocbillow.core.uikit.extension.colorInt

@BindingAdapter("app:popularityIcon")
fun popularityIcon(imageView: ImageView, popularity: Popularity) {
    val color = getAssociatedColor(popularity, imageView.context)
    ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(color))
    imageView.setImageDrawable(getDrawablePopularity(popularity, imageView.context))
}

@BindingAdapter(value = ["app:hideIfZero"])
fun hideIfZero(view: View, number: Int) {
    view.visibility = if (number == 0) View.GONE else View.VISIBLE
}

@BindingAdapter("app:progressTint")
fun progressTint(progressBar: ProgressBar, popularity: Popularity) {
    val color = getAssociatedColor(popularity, progressBar.context)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter(value = ["app:progressScaled", "android:max"], requireAll = true)
fun progressScaled(progressBar: ProgressBar, likes: Int, max: Int) {
    progressBar.progress = (likes * max / 5).coerceAtMost(max)
}

fun getDrawablePopularity(popularity: Popularity, context: Context) = when (popularity) {
    Popularity.NORMAL -> {
        ContextCompat.getDrawable(context, R.drawable.ic_person_black_96dp)
    }
    Popularity.POPULAR -> {
        ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
    }
    Popularity.STAR -> {
        ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
    }
}

fun getAssociatedColor(popularity: Popularity, context: Context) = when (popularity) {
    Popularity.NORMAL -> context.theme.obtainStyledAttributes(intArrayOf(android.R.attr.colorForeground))
        .getColor(0, 0x000000)
    Popularity.POPULAR -> R.color.popular
    Popularity.STAR -> R.color.star.colorInt
}

@BindingAdapter("numberOfSets")
fun EditText.setNumberOfSets(value: String) {
    val oldValue = text.toString()
    if (value != oldValue) {
        setText(value)
    }
}

@InverseBindingAdapter(attribute = "numberOfSets")
fun EditText.getNumberOfSets() = text.toString()

@BindingAdapter("numberOfSetsAttrChanged")
fun EditText.setFocusChangeListener(listener: InverseBindingListener?) {
    onFocusChangeListener = View.OnFocusChangeListener { focusedView, hasFocus ->
        val editText = focusedView as EditText
        if (hasFocus) {
            editText.setText("")
        } else {
            listener?.onChange()
        }
    }
}

@BindingAdapter("loseFocusWhen")
fun EditText.loseFocusWhen(condition: Boolean) {
    if (condition) clearFocus()
}

@BindingAdapter("hideKeyboardOnInputDone")
fun EditText.hideKeyboardOnInputDone(enabled: Boolean) {
    if (!enabled) return
    val listener = TextView.OnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            clearFocus()
            hideSoftInput()
        }
        false
    }
    setOnEditorActionListener(listener)
}

@BindingAdapter("clearOnFocusAndDispatch")
fun EditText.clearOnFocusAndDispatch(listener: View.OnFocusChangeListener?) {
    onFocusChangeListener = View.OnFocusChangeListener { focusView, hasFocus ->
        val textView = focusView as TextView
        if (hasFocus) {
            textView.setTag(R.id.previous_value, textView.text)
            textView.text = ""
        } else {
            if (textView.text.isEmpty()) {
                val previousValue = textView.tag as CharSequence?
                textView.text = previousValue ?: ""
            }
            listener?.onFocusChange(focusView, hasFocus)
        }
    }
}
