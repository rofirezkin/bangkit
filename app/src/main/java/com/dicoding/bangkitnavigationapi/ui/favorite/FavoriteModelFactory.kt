package com.dicoding.bangkitnavigationapi.ui.favorite

import com.dicoding.bangkitnavigationapi.data.UserRepository
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.dicoding.bangkitnavigationapi.di.Injection

class FavoriteModelFactory private constructor(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FavoriteModelFactory? = null
        fun getInstance(context: Context): FavoriteModelFactory =
            instance ?: synchronized(this) {
                instance ?: FavoriteModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}