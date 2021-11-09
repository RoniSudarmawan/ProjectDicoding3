package com.example.project3dicoding.userInterface.favoriteActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project3dicoding.database.Favorite
import com.example.project3dicoding.databinding.ActivityFavoriteBinding
import com.example.project3dicoding.helper.ViewModelFactory
import com.example.project3dicoding.userInterface.adapter.FavoriteUserAdapter
import com.example.project3dicoding.userInterface.detailActivity.DetailUserActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        showLoading(true)
        favoriteViewModel.getAllFavorite().observe(this, { favoriteList ->
            showLoading(false)

            if (favoriteList.isEmpty())
                isFavoriteEmpty(true)
            else
                isFavoriteEmpty(false)

            if (favoriteList != null) {
                adapter = FavoriteUserAdapter(favoriteList)

                binding.rvListFavorite.layoutManager = LinearLayoutManager(this)
                binding.rvListFavorite.setHasFixedSize(true)
                binding.rvListFavorite.adapter = adapter

                adapter.setOnItemClickCallback(object : FavoriteUserAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: Favorite) {
                        val moveWithDataIntent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                        moveWithDataIntent.putExtra("data", data.username)
                        startActivity(moveWithDataIntent)
                    }
                }
                )

                adapter.setOnButtonClickCallback(object : FavoriteUserAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: Favorite) {
                        favoriteViewModel.delete(data)
                    }
                }
                )
            }
        })
    }

    private fun isFavoriteEmpty(isFavoriteEmpty : Boolean) {
        binding.tvDataKosong.visibility = if (isFavoriteEmpty)View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}