package com.example.project3dicoding

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.project3dicoding.userInterface.detailActivity.followFragment.FollowFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(position)
    }

    override fun getItemCount(): Int {
        return 2
    }
}