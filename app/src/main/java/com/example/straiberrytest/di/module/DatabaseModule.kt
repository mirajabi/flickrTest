package com.example.straiberrytest.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
//    @Provides
//    @Singleton
//    internal fun providePhotoDatabase(application: Application): StraiBerryDatabase {
//        return Room.databaseBuilder(
//            application,
//            StraiBerryDatabase::class.java,
//            StraiBerryDatabase.DB_NAME
//        ).allowMainThreadQueries().build()
//    }
//
//
//    @Provides
//    internal fun providePhotoDao(dorjDatabase: StraiBerryDatabase): MyAppDao {
//        return dorjDatabase.myAppDao
//    }
}