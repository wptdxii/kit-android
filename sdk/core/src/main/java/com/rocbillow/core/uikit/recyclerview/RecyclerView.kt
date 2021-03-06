package com.rocbillow.core.uikit.recyclerview

import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.rocbillow.core.R
import com.rocbillow.core.uikit.extension.colorInt
import com.rocbillow.core.uikit.extension.dp

fun RecyclerView.addLinearDivider(
    orientation: Int = DividerItemDecoration.VERTICAL,
    @ColorRes dividerColor: Int = R.color.blue_grey_100,
    dividerHeight: Int = 1
) {
    val itemDecoration = DividerItemDecoration(context, orientation)
    val itemDrawable = GradientDrawable().apply {
        setSize(0, dividerHeight.dp)
        setColor(dividerColor.colorInt)
    }
    itemDecoration.setDrawable(itemDrawable)
    addItemDecoration(itemDecoration)
}
