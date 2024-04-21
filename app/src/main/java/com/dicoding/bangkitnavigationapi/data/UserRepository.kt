package com.dicoding.bangkitnavigationapi.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.bangkitnavigationapi.data.local.entity.FavouriteUserEntity
import com.dicoding.bangkitnavigationapi.data.remote.response.DetailUserResponse
import com.dicoding.newsapp.data.Result
import com.dicoding.bangkitnavigationapi.data.local.room.FavouriteUserDao
import com.dicoding.bangkitnavigationapi.data.remote.response.ItemsItem
import com.dicoding.bangkitnavigationapi.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(
    private val apiService: ApiService,
    private val newsDao: FavouriteUserDao
) {

    fun setUserDetail(username: String): LiveData<Result<DetailUserResponse>> {
        val result = MutableLiveData<Result<DetailUserResponse>>()
        result.value = Result.Loading

        val client = apiService.getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {
                if (response.isSuccessful) {
                    val detailUserResponse = response.body()

                    if (detailUserResponse != null) {
                        result.postValue(Result.Success(detailUserResponse))
                    } else {
                        result.postValue(Result.Error("Response body is null"))
                    }
                } else {
                    result.postValue(Result.Error("Response unsuccessful"))
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                result.postValue(Result.Error(t.message ?: "Unknown error"))
            }
        })
        return result
    }


    fun listFollowers(username: String) : LiveData<Result<List<ItemsItem>>> {
        val result = MutableLiveData<Result<List<ItemsItem>>>()
        result.value = Result.Loading
        val client = apiService.getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    val detailUserResponse = response.body()

                    if (detailUserResponse != null) {
                        result.postValue(Result.Success(detailUserResponse))
                    } else {
                        result.postValue(Result.Error("Response body is null"))
                    }
                } else {
                    result.postValue(Result.Error("Response unsuccessful"))
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        return result
    }

    fun listFollowing(username: String): LiveData<Result<List<ItemsItem>>> {
        val result = MutableLiveData<Result<List<ItemsItem>>>()
        result.value = Result.Loading

        val client = apiService.getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    val detailUserResponse = response.body()

                    if (detailUserResponse != null) {
                        result.postValue(Result.Success(detailUserResponse))
                    } else {
                        result.postValue(Result.Error("Response body is null"))
                    }
                } else {
                    result.postValue(Result.Error("Response unsuccessful"))
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    return  result

    }

    fun insertUser(user: FavouriteUserEntity) {
        newsDao.insert(user)
    }

    fun deleteUser(user: FavouriteUserEntity) {
        newsDao.delete(user)
    }

    fun getUserByUsername(username:String): LiveData<FavouriteUserEntity> {
      return  newsDao.getFavoriteUserByUsername(username)
    }

    fun getAllData(): LiveData<List<FavouriteUserEntity>> {
        return newsDao.getUsers()
    }


    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService, newsDao: FavouriteUserDao): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, newsDao)
            }.also { instance = it }
    }
}
