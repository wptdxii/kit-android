package com.rocbillow.core.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment

/**
 * @author rocbillow
 * @date 2020-08-07
 */
abstract class BaseDialogFragment(@LayoutRes resId: Int) : DialogFragment(resId) {

  constructor() : this(0)
}
