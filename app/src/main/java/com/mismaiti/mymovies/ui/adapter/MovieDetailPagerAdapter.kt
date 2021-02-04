package com.mismaiti.mymovies.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mismaiti.mymovies.ui.moviedetail.DetailCommentFragment
import com.mismaiti.mymovies.ui.moviedetail.DetailInfoFragment

class MovieDetailPagerAdapter(fragment: FragmentActivity?, private val movieId: Long)
    : FragmentStateAdapter(fragment!!) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                DetailInfoFragment(movieId)
            }
            else -> {
                DetailCommentFragment(movieId)
            }
        }
    }
}