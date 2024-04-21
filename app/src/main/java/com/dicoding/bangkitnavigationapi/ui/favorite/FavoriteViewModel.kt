package com.dicoding.bangkitnavigationapi.ui.favorite

import androidx.lifecycle.ViewModel
import com.dicoding.bangkitnavigationapi.data.UserRepository

class FavoriteViewModel (private val userRepository: UserRepository) : ViewModel(){

    fun getUserDetail() = userRepository.getAllData()

}