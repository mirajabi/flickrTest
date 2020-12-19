package com.example.straiberrytest.di.module

import com.example.straiberrytest.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
interface ActivityModule {

    @ContributesAndroidInjector(
        modules = [
//            RegisterStepOneProvider::class,
//            RegisterStepTwoProvider::class,
//            RegisterStepThreeProvider::class
        ]
    )
    fun registerActivityInjector(): MainActivity
}