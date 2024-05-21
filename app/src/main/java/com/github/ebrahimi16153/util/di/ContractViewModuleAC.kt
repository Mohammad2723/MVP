package com.github.ebrahimi16153.util.di

import android.app.Activity
import com.github.ebrahimi16153.ui.main.MainContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
class ContractViewModuleAC {

    @Provides
    fun mainView(activity:Activity):MainContract.View = activity as MainContract.View

}