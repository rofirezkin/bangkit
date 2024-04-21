package com.dicoding.bangkitnavigationapi.ui.detail
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.bangkitnavigationapi.R
import com.dicoding.bangkitnavigationapi.data.local.entity.FavouriteUserEntity
import com.dicoding.bangkitnavigationapi.data.remote.response.ItemsItem
import com.dicoding.bangkitnavigationapi.databinding.ActivityDetailBinding
import com.dicoding.newsapp.data.Result
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding


    private val detailViewModel by viewModels<DetailViewModel>(){
        ViewModelFactory.getInstance(application)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val intent = intent
        val item =intent.getParcelableExtra<ItemsItem>("item")

        detailViewModel.getUserDetail(item?.login?:"").observe(this) { result ->
            val imageUser: ShapeableImageView = findViewById(R.id.img_item_photo)
            val name: TextView = findViewById(R.id.text_name)
            val username: TextView = findViewById(R.id.text_username)
            val tvFollowers: TextView = findViewById(R.id.text_followers)
            val tvFollowing: TextView = findViewById(R.id.text_following)
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val user = result.data
                        name.text = user.login
                        tvFollowers.text =  resources.getString(R.string.count_followers, user.followers)
                        tvFollowing.text =  resources.getString(R.string.count_following, user.following)
                        name.text = user.name
                        username.text = user.login
                        Glide.with(this)
                            .load(user.avatarUrl)
                            .into(imageUser)
                        }
                    is Result.Error -> {
                        binding?.progressBar?.visibility = View.GONE

                    }
                }
            }


        }


        detailViewModel.resultSuccessFavourite.observe(this){
            binding.fabAdd.changeIconColor(R.color.black)
        }

        detailViewModel.resultDeleteFavourite.observe(this){
            binding.fabAdd.changeIconColor(R.color.white)
        }



        binding.fabAdd.setOnClickListener() {
            val news = FavouriteUserEntity(username=item?.login?:"", avatarUrl = item?.avatarUrl?:"")
            detailViewModel.saveUser(news)
        }
        detailViewModel.findFavorite(item?.login ?: "") {isFavorite ->
            if (isFavorite) {
                binding.fabAdd.changeIconColor(R.color.black)
            } else {
                binding.fabAdd.changeIconColor(R.color.white)
            }

        }




        // fragment
        val sectionsPagerAdapter = SectionsPagerAdapter(this, item?.login?: "")
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }






    private fun FloatingActionButton.changeIconColor(@ColorRes color: Int) {
        imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, color))
    }


    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following,
        )
    }

}

