package com.dicoding.bangkitnavigationapi.ui


import android.content.Intent
import android.os.Bundle
import android.view.View

import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bangkitnavigationapi.R
import com.dicoding.bangkitnavigationapi.data.remote.response.ItemsItem
import com.dicoding.bangkitnavigationapi.databinding.ActivityMainBinding
import com.dicoding.bangkitnavigationapi.ui.favorite.FavoriteActivity
import com.dicoding.bangkitnavigationapi.ui.setting.SettingActivity
import com.dicoding.bangkitnavigationapi.ui.setting.SettingModelFactory
import com.dicoding.bangkitnavigationapi.ui.setting.SettingPreferences
import com.dicoding.bangkitnavigationapi.ui.setting.SettingViewModel
import com.dicoding.bangkitnavigationapi.ui.setting.dataStore


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

     // setting
        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, SettingModelFactory(pref)).get(
            SettingViewModel::class.java
        )
        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                val favoriteButton = binding.favoriteButton
                val optionButton = binding.optionButton
                val drawable = ContextCompat.getDrawable(this, R.drawable.ic_fav)
                drawable?.setTint(ContextCompat.getColor(this, R.color.white))
                favoriteButton.setImageDrawable(drawable)

                val drawableOption = ContextCompat.getDrawable(this, R.drawable.option_menu)
                drawableOption?.setTint(ContextCompat.getColor(this,R.color.white))
                optionButton.setImageDrawable(drawableOption)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

            }


        }

        binding.optionButton.setOnClickListener(){
            val popupMenu = PopupMenu(this, it)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId){
                    R.id.setting ->  {
                        Intent(this, SettingActivity::class.java).apply {
                            startActivity(this)
                        }
                        true
                    }
                    else -> {
                        true
                    }
                }
            }
            popupMenu.inflate(R.menu.menu)
            popupMenu.show()
        }

        supportActionBar?.hide()
        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    val searchText = searchView.text.toString() // Menyimpan teks searchView sebelum di-hide
                    searchBar.setText(searchText)
                    searchView.hide()

                    mainViewModel.findUser(searchView.text.toString())
                    mainViewModel.isLoading.observe(this@MainActivity) { it
                        showLoading(it)
                    }
                    false
                }


        }


        binding.favoriteButton.setOnClickListener(){
            Intent(this, FavoriteActivity::class.java).apply {
                startActivity(this)
            }
        }


        val layoutManager = LinearLayoutManager(this)
        binding.rvMain.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvMain.addItemDecoration(itemDecoration)
        mainViewModel.listItem.observe(this) { user ->
            if(user.isEmpty()) {
                binding.textEmptyState.visibility = View.VISIBLE
                binding.rvMain.visibility = View.GONE
            } else {
                binding.rvMain.visibility = View.VISIBLE
                binding.textEmptyState.visibility = View.GONE
                setUserData(user)
            }

        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }



    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun setUserData(user: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(user)
        binding.rvMain.adapter = adapter
    }


}