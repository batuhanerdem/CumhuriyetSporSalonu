package com.example.cumhuriyetsporsalonu.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

class Module {
    @Provides
    @Singleton
    fun provideFirebaseInstance(): FirebaseAuth {
        return Firebase.auth
    }
}