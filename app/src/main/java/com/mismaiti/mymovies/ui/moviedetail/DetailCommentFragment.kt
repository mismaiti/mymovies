package com.mismaiti.mymovies.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mismaiti.mymovies.databinding.FragmentItemMovieCommentBinding
import com.mismaiti.mymovies.ui.adapter.MovieReviewAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DetailCommentFragment(private val movieId: Long): DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var detailInfoViewModel: DetailInfoViewModel
    private var fragmentBinding: FragmentItemMovieCommentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailInfoViewModel =
            ViewModelProviders.of(this,viewModelFactory).get(DetailInfoViewModel::class.java)
        fragmentBinding = FragmentItemMovieCommentBinding.inflate(inflater, container, false)
        fragmentBinding!!.rvMovieComments.layoutManager = LinearLayoutManager(context)

        detailInfoViewModel.getListMovieComment(movieId).observe(viewLifecycleOwner, Observer {
            fragmentBinding!!.rvMovieComments.adapter = MovieReviewAdapter(context, it.results)
        })
        return fragmentBinding!!.root
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}