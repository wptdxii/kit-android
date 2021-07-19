package com.olaroc.component.sample.databinding

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.animation.ArgbEvaluatorCompat
import com.olaroc.component.R
import com.olaroc.core.uikit.extension.colorInt

object AnimationBindingAdapters {

    private const val VERTICAL_BIAS_ANIMATION_DURATION = 900L

    private const val BG_COLOR_ANIMATION_DURATION = 500L

    @BindingAdapter(value = ["animateBackground", "animateBackgroundStage"], requireAll = true)
    @JvmStatic
    fun animateBackground(view: View, timerRunning: Boolean, workingStage: Boolean) {
        if (!timerRunning) {
            view.setBackgroundColor(R.color.disabledInputColor.colorInt)
            view.setTag(R.id.hasBeenAnimated, false)
            return
        }

        if (workingStage) {
            animateBackgroundColor(view, true)
            view.setTag(R.id.hasBeenAnimated, true)
        } else {
            val hasBeenAnimated = view.getTag(R.id.hasBeenAnimated) as Boolean?
            if (hasBeenAnimated == true) {
                animateBackgroundColor(view, false)
                view.setTag(R.id.hasBeenAnimated, false)
            }
        }
    }

    private fun animateBackgroundColor(view: View, tint: Boolean) {
        val colorEnabled = R.color.enabledInputColor.colorInt
        val colorDisabled = R.color.disabledInputColor.colorInt
        val startColor = if (tint) colorDisabled else colorEnabled
        val endColor = if (tint) colorEnabled else colorDisabled
        val animator = ObjectAnimator.ofObject(
            view,
            "backgroundColor",
            ArgbEvaluatorCompat(),
            startColor,
            endColor
        )
        animator.duration = BG_COLOR_ANIMATION_DURATION
        animator.start()
    }

    @BindingAdapter(value = ["animateVerticalBias","animateVerticalBiasStage"])
    @JvmStatic
    fun ProgressBar.animateVerticalBias(timerRunning: Boolean, workingStage: Boolean) = when {
        timerRunning && workingStage -> animateBias(this, 0.6F)
        timerRunning && !workingStage -> animateBias(this, 0.4F)
        else -> animateBias(this, 0.5F)
    }

    private fun animateBias(progressBar: ProgressBar, bias: Float) {
        val layoutParams = progressBar.layoutParams
        if (layoutParams is ConstraintLayout.LayoutParams) {
            val verticalBias = layoutParams.verticalBias
            val animator = ValueAnimator.ofFloat(verticalBias, bias)
            animator.addUpdateListener { animation ->
                val animatedValue = animation.animatedValue as Float
                layoutParams.verticalBias = animatedValue
                progressBar.requestLayout()
            }
            animator.interpolator = DecelerateInterpolator()
            animator.duration = VERTICAL_BIAS_ANIMATION_DURATION
            animator.start()
        }
    }
}