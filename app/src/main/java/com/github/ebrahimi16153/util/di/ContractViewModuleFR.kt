package com.github.ebrahimi16153.util.di

import androidx.fragment.app.Fragment
import com.github.ebrahimi16153.ui.add.NoteContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent


@Module
@InstallIn(FragmentComponent::class)
class ContractViewModuleFR {

    @Provides
    fun noteFragment(fragment: Fragment) : NoteContract.View = fragment as NoteContract.View


}