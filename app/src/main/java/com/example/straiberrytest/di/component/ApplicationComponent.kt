package com.example.straiberrytest.di.component

import android.app.Application
import com.example.straiberrytest.MainApplication
import com.example.straiberrytest.di.module.ActivityModule
import com.example.straiberrytest.di.module.ApplicationModule
import com.example.straiberrytest.di.module.DatabaseModule
import com.example.straiberrytest.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        DatabaseModule::class,
        NetworkModule::class
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: MainApplication)
}