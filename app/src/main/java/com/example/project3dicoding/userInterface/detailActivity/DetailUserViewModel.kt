package com.example.project3dicoding.userInterface.detailActivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project3dicoding.ApiConfig
import com.example.project3dicoding.getAPI.DetailUserResponse
import com.example.project3dicoding.getAPI.FollowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {
    private val _responseBodyDetail = MutableLiveData<DetailUserResponse>()
    val responseBodyDetail: LiveData<DetailUserResponse> = _responseBodyDetail

    private val _responseBodyFollower = MutableLiveData<List<FollowResponse>>()
    val responseBodyFollower: LiveData<List<FollowResponse>> = _responseBodyFollower

    private val _responseBodyFollowing = MutableLiveData<List<FollowResponse>>()
    val responseBodyFollowing: LiveData<List<FollowResponse>> = _responseBodyFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    companion object{
        private const val TAG = "DetailViewModel"
    }

    fun findDetail(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBodyDetail = response.body()
                        _responseBodyDetail.value = responseBodyDetail!!
                } else {
                    _isError.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun findFollower(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollower(username)
        client.enqueue(object : Callback<List<FollowResponse>> {
            override fun onResponse(
                call: Call<List<FollowResponse>>,
                response: Response<List<FollowResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                        val responseBodyFollower = response.body()
                        _responseBodyFollower.value = responseBodyFollower!!
                } else {
                    _isError.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<FollowResponse>>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }



    fun findFollowing(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<FollowResponse>> {
            override fun onResponse(
                call: Call<List<FollowResponse>>,
                response: Response<List<FollowResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBodyFollowing = response.body()
                    _responseBodyFollowing.value = responseBodyFollowing!!
                } else {
                    _isError.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<FollowResponse>>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}