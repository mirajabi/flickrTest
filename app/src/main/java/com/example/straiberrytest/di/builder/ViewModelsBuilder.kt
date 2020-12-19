package com.example.straiberrytest.di.builder

import androidx.lifecycle.ViewModel
import com.example.straiberrytest.di.scope.ViewModelKey
import com.example.straiberrytest.presentation.StrAiBerryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelsBuilder {

    @Binds
    @IntoMap
    @ViewModelKey(StrAiBerryViewModel::class)
    abstract fun bindRegisterStepTwoViewModel(mStrAiBerryViewModels: StrAiBerryViewModel): ViewModel

}