package com.rocbillow.base.generic

import androidx.fragment.app.Fragment

/**
 * @author rocbillow
 * @date 2020-08-07
 */
abstract class BaseFragment : Fragment {

  constructor() : super()

  constructor(layoutId: Int) : super(layoutId)
}
