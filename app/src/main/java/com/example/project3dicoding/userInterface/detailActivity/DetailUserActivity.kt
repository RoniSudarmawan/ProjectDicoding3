package com.example.project3dicoding.userInterface.detailActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.project3dicoding.R
import com.example.project3dicoding.SectionsPagerAdapter
import com.example.project3dicoding.database.Favorite
import com.example.project3dicoding.userInterface.detailActivity.followFragment.FollowFragment
import com.example.project3dicoding.getAPI.DetailUserResponse
import com.example.project3dicoding.databinding.ActivityDetailUserBinding
import com.example.project3dicoding.helper.ViewModelFactory
import com.example.project3dicoding.userInterface.favoriteActivity.AddFavoriteViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "DetailUserActivity"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var addFavoriteViewModel: AddFavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("data")

        val detailUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)
        addFavoriteViewModel = obtainViewModel(this@DetailUserActivity)

        detailUserViewModel.responseBodyDetail.observe(this) { user ->
            setDetailData(user)
        }

        detailUserViewModel.isLoading.observe(this){
            showLoading(it)
        }

        detailUserViewModel.isError.observe(this){
            if(it)
                showError()
        }

        if (username != null) {
            detailUserViewModel.findDetail(username)
        }

    }

    private fun setDetailData(data: DetailUserResponse) {
        Glide.with(this@DetailUserActivity)
            .load(data.avatarUrl)
            .circleCrop()
            .into(binding.tvAvatarDetail)

        if (data.name != null)
            binding.tvNameDetail.text = data.name
        else
            binding.tvNameDetail.text = "-"

        binding.tvUsernameDetail.text = ("(${data.login})")

        if (data.location != null)
            binding.tvLocationDetail.text = data.location
        else
            binding.tvLocationDetail.text = "-"

        if (data.publicRepos != null)
            binding.tvRepositoryDetail.text = data.publicRepos.toString()
        else
            binding.tvRepositoryDetail.text = "-"

        if (data.company != null)
            binding.tvCompanyDetail.text = data.company
        else
            binding.tvCompanyDetail.text = "-"

        FollowFragment.USERNAME = data.login!!
        val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailUserActivity)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        binding.fabFavorite.setOnClickListener {
            val fav = Favorite()
            fav.let { user ->
                user.id = data.id!!
                user.username = data.login
                user.avatar = data.avatarUrl
                user.link = data.url}
            addFavoriteViewModel.insert(fav)
            Toast.makeText(this,"Berhasil Ditambahkan ke Favorit", Toast.LENGTH_LONG).show()
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): AddFavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(AddFavoriteViewModel::class.java)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    
    private fun showError(){
        Toast.makeText(this, "Terjadi Error", Toast.LENGTH_SHORT).show()
    }
}

