package com.dxii.doraemon

import com.dxii.basekit.base.BaseApplication
import dagger.hilt.android.HiltAndroidApp

/**
 * @author dxii
 * @date 2020-09-04
 */
@HiltAndroidApp
class DoraemonApp : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksImpl())
    }
}
