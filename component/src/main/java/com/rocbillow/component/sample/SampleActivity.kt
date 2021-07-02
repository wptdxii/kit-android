package com.rocbillow.component.sample

import android.os.Bundle
import com.rocbillow.component.R
import com.rocbillow.component.databinding.ActivitySampleBinding
import com.rocbillow.core.base.BaseActivity
import com.rocbillow.core.binding.dataBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleActivity : BaseActivity() {

    private val dataBinding by dataBinding<ActivitySampleBinding>(R.layout.activity_sample)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.executePendingBindings()
    }
}