package com.example.straiberrytest.di.provider

import com.example.straiberrytest.domain.model.PhotoDetail
import com.example.straiberrytest.presentation.details.DetailsPhoto
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailsPhotoProvider{

    @ContributesAndroidInjector
    abstract fun provideDetailFragment(): DetailsPhoto
}