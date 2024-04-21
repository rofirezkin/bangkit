package com.dicoding.bangkitnavigationapi.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bangkitnavigationapi.data.remote.response.ItemsItem
import com.dicoding.bangkitnavigationapi.databinding.ActivityFavoriteBinding
import com.dicoding.bangkitnavigationapi.ui.UserAdapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>(){
        FavoriteModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)
        favoriteViewModel.getUserDetail().observe(this){result->
            val itemsItemsList = result.map { favouriteUserEntity ->
                ItemsItem(
                    login = favouriteUserEntity.username,
                    avatarUrl = favouriteUserEntity.avatarUrl?: ""
                    // Tambahkan properti lain jika diperlukan
                )
            }
            setUserData(itemsItemsList)
        }
    }
    private fun setUserData(consumerReviews: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(consumerReviews)
        binding.rvFavorite.adapter = adapter
    }
}