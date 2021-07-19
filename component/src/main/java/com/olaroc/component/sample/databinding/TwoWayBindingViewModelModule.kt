package com.olaroc.component.sample.databinding

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object TwoWayBindingViewModelModule {

    @Provides
    fun provideTimer(): ITimer = DefaultTimer

}