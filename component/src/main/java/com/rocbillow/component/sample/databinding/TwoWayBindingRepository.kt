package com.rocbillow.component.sample.databinding

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TwoWayBindingRepository @Inject constructor(private val prefsStore: PrefsStore) :
    PrefsStore by prefsStore