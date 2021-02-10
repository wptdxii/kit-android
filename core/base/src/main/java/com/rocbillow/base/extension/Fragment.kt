package com.rocbillow.base.extension

import android.os.*
import androidx.fragment.app.Fragment

/**
 * @see android.os.MessageQueue.IdleHandler.queueIdle
 */
@JvmOverloads
fun Fragment.doOnMainThreadIdle(timeout: Long? = null,action: () -> Unit) {
  val handler = Handler(Looper.getMainLooper())

  val idleHandler = MessageQueue.IdleHandler {
    handler.removeCallbacksAndMessages(null)
    action()
    return@IdleHandler false
  }

  fun setupIdleHandler(queue: MessageQueue) {
    if (timeout != null) {
      handler.postDelayed({
        queue.removeIdleHandler(idleHandler)
        action()
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

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    setupIdleHandler(Looper.getMainLooper().queue)
    return
  }
  handler.post { setupIdleHandler(Looper.myQueue()) }
}