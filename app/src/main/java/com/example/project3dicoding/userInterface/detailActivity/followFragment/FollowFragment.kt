package com.example.project3dicoding.userInterface.detailActivity.followFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project3dicoding.userInterface.adapter.FavoriteUserAdapter
import com.example.project3dicoding.getAPI.FollowResponse
import com.example.project3dicoding.databinding.FragmentFollowBinding
import com.example.project3dicoding.userInterface.adapter.GithubUserAdapter
import com.example.project3dicoding.userInterface.adapter.ListGithubMain
import com.example.project3dicoding.userInterface.detailActivity.DetailUserActivity
import com.example.project3dicoding.userInterface.detailActivity.DetailUserViewModel


class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding

    companion object {
        lateinit var USERNAME : String
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val TAG = "Follow Fragment"


        @JvmStatic
        fun newInstance(index: Int) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(layoutInflater,container,false)

        val detailUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailUserViewModel::class.java)

        detailUserViewModel.responseBodyFollower.observe(viewLifecycleOwner) {user ->
            setFollowData(user)
        }

        detailUserViewModel.responseBodyFollowing.observe(viewLifecycleOwner) {user ->
            setFollowData(user)
        }

        detailUserViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        if (arguments?.getInt(ARG_SECTION_NUMBER, 0) == 0) {
            detailUserViewModel.findFollower(USERNAME)
        }
        else if (arguments?.getInt(ARG_SECTION_NUMBER, 0) == 1){
            detailUserViewModel.findFollowing(USERNAME)
        }
        return binding.root
    }

    private fun setFollowData(item: List<FollowResponse>) {
        val data = ArrayList<ListGithubMain>()
        for (i in item){
            data.add(ListGithubMain(i.id!!, i.login!!, i.url!!, i.avatarUrl!!))
        }

        binding.rvListGithub.layoutManager = LinearLayoutManager(context)
        val listGithubAdapter = GithubUserAdapter(data)
        binding.rvListGithub.adapter = listGithubAdapter

        listGithubAdapter.setOnItemClickCallback(object : GithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: String) {
                val moveWithDataIntent = Intent(context, DetailUserActivity::class.java)
                moveWithDataIntent.putExtra("data", data)
                startActivity(moveWithDataIntent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}