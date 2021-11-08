package com.example.project3dicoding.userInterface.favoriteActivity

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.project3dicoding.database.Favorite
import com.example.project3dicoding.repository.FavoriteRepository

class AddFavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    fun insert(favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }

}