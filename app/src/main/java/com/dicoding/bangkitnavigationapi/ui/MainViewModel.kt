package com.dicoding.bangkitnavigationapi.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.bangkitnavigationapi.data.remote.response.GithubResponse
import com.dicoding.bangkitnavigationapi.data.remote.response.ItemsItem
import com.dicoding.bangkitnavigationapi.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listItem = MutableLiveData<List<ItemsItem>>()
    val listItem: LiveData<List<ItemsItem>> = _listItem
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    companion object{
        private const val TAG = "MainViewModel"
    }
    init {
        findUser(username = "arif")
    }





    fun findUser(username:String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(name = username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listItem.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailurex: ${t.message.toString()}")
            }
        })
    }



}