package com.example.project3dicoding.userInterface.mainActivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project3dicoding.ApiConfig
import com.example.project3dicoding.GithubUserResponse
import com.example.project3dicoding.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _responseBody = MutableLiveData<List<ItemsItem>>()
    val responseBody: LiveData<List<ItemsItem>> = _responseBody

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    companion object{
        private const val TAG = "MainViewModel"
    }


    fun findUser(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserSearch(username)
        client.enqueue(object : Callback<GithubUserResponse> {
            override fun onResponse(
                call: Call<GithubUserResponse>,
                response: Response<GithubUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _responseBody.value = response.body()?.items
                } else {
                    _isError.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubUserResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}