package com.dicoding.bangkitnavigationapi.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GithubResponse(
	@field:SerializedName("items")
	val items: List<ItemsItem>
)
@Parcelize
data class ItemsItem(
	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

) : Parcelable
