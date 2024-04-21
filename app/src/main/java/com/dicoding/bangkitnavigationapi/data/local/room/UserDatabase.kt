package com.dicoding.bangkitnavigationapi.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.bangkitnavigationapi.data.local.entity.FavouriteUserEntity


@Database(entities = [FavouriteUserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun newsDao(): FavouriteUserDao

    companion object {
        @Volatile
        private var instance: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java, "user.db"
                ).allowMainThreadQueries().build()
            }
    }
}