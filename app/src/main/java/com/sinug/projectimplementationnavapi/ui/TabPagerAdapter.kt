package com.sinug.projectimplementationnavapi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sinug.projectimplementationnavapi.FollowerFollowingFragment

class TabPagerAdapter(activity: FragmentActivity, private val username: String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }


    override fun createFragment(position: Int): Fragment {
        val args = Bundle().apply {
            putString(FollowerFollowingFragment.ARG_USERNAME, username)
            putInt(FollowerFollowingFragment.ARG_SECTION_NUMBER, position)
        }

        return when (position) {
            0, 1 -> {
                val fragment = FollowerFollowingFragment()
                fragment.arguments = args
                fragment
            }
            else -> throw IllegalArgumentException("Invalid tab position: $position")
        }
    }
}