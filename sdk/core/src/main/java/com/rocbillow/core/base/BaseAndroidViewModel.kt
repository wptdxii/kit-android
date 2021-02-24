package com.rocbillow.core.base

import android.app.Application
import androidx.annotation.Nullable
import androidx.lifecycle.AndroidViewModel

/**
 * @author rocbillow
 * @date 2020-08-07
 */
abstract class BaseAndroidViewModel(@Nullable application: Application) :
  AndroidViewModel(application)
