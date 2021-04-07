package com.mismaiti.mymovies.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mismaiti.mymovies.databinding.FragmentMoviedetailBinding
import com.mismaiti.mymovies.ui.adapter.MovieDetailPagerAdapter

class MovieDetailFragment : Fragment() {

    lateinit var fragmentBinding: FragmentMoviedetailBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        fragmentBinding = FragmentMoviedetailBinding.inflate(inflater, container, false)
        with(fragmentBinding) {
            vpMovieDetail.adapter = arguments?.let { MovieDetailPagerAdapter(activity, it.getLong("movieId")) }
            tlMovieDetail.let {
                TabLayoutMediator(it, vpMovieDetail) { tab, position ->
                    tab.text = when(position) {
                        0 -> "Informasi"
                        else -> "Komentar"
                    }
                }.attach()
            }
        }

        return fragmentBinding.root
    }

    override fun onDestroyView() {
        fragmentBinding.reset()
        super.onDestroyView()
    }
}

fun FragmentMoviedetailBinding.reset() {
    vpMovieDetail.adapter = null
}