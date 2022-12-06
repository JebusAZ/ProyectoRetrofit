package com.example.proyectofinal.di

import com.example.proyectofinal.datasource.RestDataSource
import com.example.proyectofinal.repository.UserRepository
import com.example.proyectofinal.repository.UserRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Url
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun userRepository(repo:UserRepositoryImp): UserRepository



}