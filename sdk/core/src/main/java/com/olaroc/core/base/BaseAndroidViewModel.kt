package com.olaroc.core.base

import android.app.Application
import androidx.annotation.Nullable
import androidx.lifecycle.AndroidViewModel

/**
 * @author olaroc
 * @date 2020-08-07
 */
abstract class BaseAndroidViewModel(@Nullable application: Application) :
  AndroidViewModel(application)
