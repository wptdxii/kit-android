package com.rocbillow.core.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * @author rocbillow
 * @date 2020-08-07
 */
abstract class BaseFragment(@LayoutRes resId: Int) : Fragment(resId) {

  constructor() : this(0)
}
