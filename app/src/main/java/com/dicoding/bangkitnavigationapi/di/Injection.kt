package com.dicoding.bangkitnavigationapi.di

import com.dicoding.bangkitnavigationapi.data.UserRepository
import android.content.Context
import com.dicoding.bangkitnavigationapi.data.remote.retrofit.ApiConfig
import com.dicoding.bangkitnavigationapi.data.local.room.UserDatabase


object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.newsDao()
        return UserRepository.getInstance(apiService, dao)
    }
}