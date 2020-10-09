package com.dxii.basekit.base

import android.app.Application
import androidx.annotation.Nullable
import androidx.lifecycle.AndroidViewModel

/**
 * @author idxii
 * @date 2020-08-07
 */
abstract class BaseAndroidViewModel(@Nullable application: Application) :
    AndroidViewModel(application)
