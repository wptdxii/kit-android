package com.olaroc.component.scan

import android.Manifest
import android.os.Bundle
import android.view.View
import com.olaroc.component.R
import com.olaroc.component.databinding.ActivityScanBinding
import com.olaroc.core.base.BaseActivity
import com.olaroc.core.binding.bindView
import com.olaroc.core.extension.setOnThrottleClickListener
import com.olaroc.core.systembar.applyStatusBarInsetsToMargin
import com.olaroc.core.systembar.applyStatusBarInsetsToPadding
import com.olaroc.core.uikit.extension.arrow
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanActivity : BaseActivity(R.layout.activity_scan) {

    companion object {
        const val TAG = "Scan"
    }

    private val viewBinding by bindView { contentView -> ActivityScanBinding.bind(contentView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindUi()
    }

    override fun applySystemWindows() {
        super.applySystemWindows()
        viewBinding.layer.applyStatusBarInsetsToPadding()
        viewBinding.toolbar.applyStatusBarInsetsToMargin()
    }

    private fun bindUi() {
        with(viewBinding.toolbar) {
            title = TAG
            navigationIcon = arrow()
            setNavigationOnClickListener { onBackPressed() }
        }
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