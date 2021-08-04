package com.olaroc.component.scan

import android.Manifest
import android.os.Bundle
import android.view.View
import com.olaroc.component.R
import com.olaroc.component.databinding.ActivityScanBinding
import com.olaroc.core.base.BaseActivity
import com.olaroc.core.binding.bindView
import com.olaroc.core.extension.setOnThrottleClickListener
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanActivity : BaseActivity(R.layout.activity_scan) {

    private val viewBinding by bindView { contentView -> ActivityScanBinding.bind(contentView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindUi()
    }

    private fun bindUi() {
        viewBinding.btnHmsScankit.click {
            scanWithDefaultViewMode()
        }
    }

    private fun scanWithDefaultViewMode() {

    }

    private inline fun View.click(crossinline action: () -> Unit) {
        setOnThrottleClickListener { requestPermission { action() } }
    }

    private inline fun requestPermission(crossinline action: () -> Unit) {
        PermissionX.init(this)
            .permissions(Manifest.permission.CAMERA)
            .request { allGranted, _, _ ->
                if (allGranted) {
                    action()
                }
            }
    }
}