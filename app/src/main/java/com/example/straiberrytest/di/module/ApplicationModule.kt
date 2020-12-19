package com.example.straiberrytest.di.module

import android.app.Application
import android.content.Context
import com.example.straiberrytest.di.builder.ViewModelFactoryBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelFactoryBuilder::class])
class ApplicationModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

}