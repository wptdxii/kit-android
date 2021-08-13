package com.olaroc.component.sample

import android.os.Bundle
import com.olaroc.component.R
import com.olaroc.component.databinding.ActivitySampleBinding
import com.olaroc.core.base.BaseActivity
import com.olaroc.core.binding.dataBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleActivity : BaseActivity() {

    private val dataBinding by dataBinding<ActivitySampleBinding>(R.layout.activity_sample)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.executePendingBindings()
//        ViewCompat.setOnApplyWindowInsetsListener(dataBinding.fragmentContainer) { view, insets ->
//            var consumed = false
//            (view as ViewGroup).forEach { child ->
//                val childInsets = child.dispatchApplyWindowInsets(insets.toWindowInsets())
//                if (childInsets.isConsumed) {
//                    consumed = true
//                }
//            }
//            if (consumed) WindowInsetsCompat.CONSUMED else insets
//        }
    }
}