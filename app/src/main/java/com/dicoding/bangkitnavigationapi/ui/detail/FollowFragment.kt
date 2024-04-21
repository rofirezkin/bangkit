package com.dicoding.bangkitnavigationapi.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels


import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bangkitnavigationapi.databinding.FragmentFollowBinding
import com.dicoding.bangkitnavigationapi.ui.UserAdapter
import com.dicoding.newsapp.data.Result

class FollowFragment : Fragment() {

    private var position: Int = 0
    private var username: String? = ""
    private lateinit var binding: FragmentFollowBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val detailViewModel: DetailViewModel by viewModels {
            factory
        }

        arguments?.let {
            position = it.getInt(ARG_SECTION_NUMBER, 0)
            username = it.getString(ARG_SECTION_USERNAME)
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.recyclerView.addItemDecoration(itemDecoration)

        if (position == 1) {
            detailViewModel.getListFollowers(username.toString()).observe(viewLifecycleOwner){ result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding?.progressBarFragment?.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding?.progressBarFragment?.visibility = View.GONE
                            val adapter = UserAdapter()
                            adapter.submitList(result.data)
                            binding.recyclerView.adapter = adapter

                        }
                        is Result.Error -> {
                            binding?.progressBarFragment?.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            }
        } else {
            detailViewModel.getListFollowing(username.toString()).observe(viewLifecycleOwner){ result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding?.progressBarFragment?.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding?.progressBarFragment?.visibility = View.GONE
                            val adapter = UserAdapter()
                            adapter.submitList(result.data)
                            binding.recyclerView.adapter = adapter

                        }
                        is Result.Error -> {
                            binding?.progressBarFragment?.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            }
        }


    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFragment.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showEmptyState() {
        // Tampilkan pesan atau tindakan yang sesuai
        // Contoh: menampilkan pesan "Tidak ada data yang tersedia"
        Toast.makeText(requireContext(), "Tidak ada data yang tersedia", Toast.LENGTH_SHORT).show()
    }
    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_SECTION_USERNAME = "section_username"
    }
}