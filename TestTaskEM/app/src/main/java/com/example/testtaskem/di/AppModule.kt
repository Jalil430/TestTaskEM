package com.example.testtaskem.di

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.common.Constants.SHARED_PREF
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext appContext: Context) : SharedPreferences =
        appContext.getSharedPreferences(
            SHARED_PREF,
            AppCompatActivity.MODE_PRIVATE
        )
}