package com.dicoding.bangkitnavigationapi.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey




@Entity(tableName = "User")
data class FavouriteUserEntity(
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var avatarUrl: String? = null,
)
