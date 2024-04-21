package com.dicoding.bangkitnavigationapi.data.local.room


import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.bangkitnavigationapi.data.local.entity.FavouriteUserEntity


@Dao
interface FavouriteUserDao {
    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    fun insert(user: FavouriteUserEntity)

    @Query("SELECT * FROM User")
    fun getUsers(): LiveData<List<FavouriteUserEntity>>

    @Query("SELECT * FROM User WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavouriteUserEntity>

    @Delete
    fun delete(user: FavouriteUserEntity)

}