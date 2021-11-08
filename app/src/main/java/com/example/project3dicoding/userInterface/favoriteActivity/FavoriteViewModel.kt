package com.example.project3dicoding.userInterface.favoriteActivity

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.project3dicoding.database.Favorite
import com.example.project3dicoding.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun delete(favorite: Favorite) {
        mFavoriteRepository.delete(favorite)
    }

    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorite()
}