package com.hardi.cakelist.di.module

import com.hardi.cakelist.ui.cakelist.CakeListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @ActivityScoped
    @Provides
    fun provideCakeList() = CakeListAdapter(ArrayList())
}