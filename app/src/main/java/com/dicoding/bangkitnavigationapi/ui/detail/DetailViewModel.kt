package com.dicoding.bangkitnavigationapi.ui.detail

import com.dicoding.bangkitnavigationapi.data.UserRepository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.bangkitnavigationapi.data.local.entity.FavouriteUserEntity

import kotlinx.coroutines.launch

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    val resultSuccessFavourite = MutableLiveData<Boolean>()
    val resultDeleteFavourite = MutableLiveData<Boolean>()
    private var isFavourite = false

    fun getUserDetail(name: String) = userRepository.setUserDetail(name)


    fun getListFollowers(name: String) = userRepository.listFollowers(name)

    fun getListFollowing(name: String ) = userRepository.listFollowing(name)
    fun saveUser(user:FavouriteUserEntity ) {
        viewModelScope.launch {
            user?.let {
                if(isFavourite) {
                    userRepository.deleteUser(user)
                    resultDeleteFavourite.value =true

                } else {
                    userRepository.insertUser(user)
                    resultSuccessFavourite.value =true
                }
            }
        }
       isFavourite= !isFavourite
    }

    fun findFavorite(username: String, listenFavorite: (isFavorite: Boolean) -> Unit){
        println("dataa"+ username)
      userRepository.getUserByUsername(username).observeForever(){ user ->
          println("dataa keduaaaaa"+ user)
          if (user != null) {
              listenFavorite(true)
              isFavourite = true
          } else {
              listenFavorite(false)
          }
        }
    }




}