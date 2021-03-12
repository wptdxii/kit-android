package com.rocbillow.core.extension

import android.graphics.Point
import com.rocbillow.core.assist.ContextProvider

val screenWidth: Int
  get() = getDisplayPoint().x

val screenHeight: Int
  get() = getDisplayPoint().y

private fun getDisplayPoint(): Point {
  val point = Point()
  val display = ContextProvider.context.display
  display?.getRealSize(point)
  return point
}