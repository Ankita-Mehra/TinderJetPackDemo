package com.softradix.tinderjetpackdemo.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PrefModule {
    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ) = UserManager(context)
}
