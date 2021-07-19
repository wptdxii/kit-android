package com.olaroc.core.extension

import android.annotation.SuppressLint
import android.os.*
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.transition.Slide
import androidx.transition.TransitionManager

fun View.slideInEnd() {
    val target = this
    target.isInvisible = true
    doOnMainThreadIdle {
        val slide = Slide(Gravity.END).apply {
            addTarget(target)
        }
        TransitionManager.beginDelayedTransition(this.parent as ViewGroup, slide)
        target.isInvisible = false
    }
}

/**
 * @see android.os.MessageQueue.IdleHandler.queueIdle
 */
@JvmOverloads
fun View.doOnMainThreadIdle(timeout: Long? = null, action: View.() -> Unit) {
    val handler = Handler(Looper.getMainLooper())

    val idleHandler = MessageQueue.IdleHandler {
        handler.removeCallbacksAndMessages(null)
        this.action()
        return@IdleHandler false
    }

    fun setupIdleHandler(queue: MessageQueue) {
        timeout?.let { timeout ->
            handler.postDelayed({
                queue.removeIdleHandler(idleHandler)
                this.action()
            }, timeout)
        }
        queue.addIdleHandler(idleHandler)
        // in extreme case, add IdleHandle will not wake Looper,
        // send an empty message to solve the problem
        handler.sendEmptyMessage(0)
    }

    if (Looper.getMainLooper() == Looper.myLooper()) {
        setupIdleHandler(Looper.myQueue())
        return
    }

    @SuppressLint("ObsoleteSdkInt")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setupIdleHandler(Looper.getMainLooper().queue)
        return
    }
    handler.post { setupIdleHandler(Looper.myQueue()) }
}