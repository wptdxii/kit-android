package com.rocbillow.core.extension

import kotlinx.coroutines.Job
import kotlin.coroutines.coroutineContext

suspend fun Job.Key.currentJob() = coroutineContext[Job]