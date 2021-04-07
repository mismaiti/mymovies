package com.mismaiti.mymovies.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mismaiti.mymovies.databinding.FragmentItemMovieCommentBinding
import com.mismaiti.mymovies.ui.adapter.MovieReviewAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DetailCommentFragment(private val movieId: Long): DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var detailInfoViewModel: DetailInfoViewModel
    lateinit var fragmentBinding: FragmentItemMovieCommentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailInfoViewModel =
            ViewModelProvider(this,viewModelFactory).get(DetailInfoViewModel::class.java)
        fragmentBinding = FragmentItemMovieCommentBinding.inflate(inflater, container, false)

        with(fragmentBinding) {
            rvMovieComments.layoutManager = LinearLayoutManager(context)
            detailInfoViewModel.getListMovieComment(movieId).observe(viewLifecycleOwner, Observer {
                rvMovieComments.adapter = MovieReviewAdapter(context, it.results)
            })
        }

        return fragmentBinding.root
    }

    override fun onDestroyView() {
        fragmentBinding.reset()
        super.onDestroyView()
    }
}

fun FragmentItemMovieCommentBinding.reset() {
    rvMovieComments.adapter = null
}