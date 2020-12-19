package com.example.straiberrytest.di.provider

import com.example.straiberrytest.presentation.PhotosFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class PhotosFragmentProvider{

    @ContributesAndroidInjector
    abstract fun providePhotosFragment(): PhotosFragment
}