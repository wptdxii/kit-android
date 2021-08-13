package com.olaroc.core.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment

/**
 * @author olaroc
 * @date 2020-08-07
 */
abstract class BaseFragment(@LayoutRes resId: Int) : Fragment(resId) {

    constructor() : this(0)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applySystemWindows()
        ViewCompat.requestApplyInsets(view)
    }

    protected open fun applySystemWindows() {}
}
