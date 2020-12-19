package com.example.straiberrytest.di.module

import com.example.straiberrytest.MainActivity
import com.example.straiberrytest.di.provider.DetailsPhotoProvider
import com.example.straiberrytest.di.provider.PhotosFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
interface ActivityModule {

    @ContributesAndroidInjector(
        modules = [
            PhotosFragmentProvider::class,
            DetailsPhotoProvider::class
        ]
    )
    fun photoActivityInjector(): MainActivity
}