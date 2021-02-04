package com.mismaiti.mymovies.ui.moviedetail

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.mismaiti.mymovies.BuildConfig
import com.mismaiti.mymovies.R
import com.mismaiti.mymovies.databinding.FragmentItemMovieDetailBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DetailInfoFragment(private val movieId: Long) : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var detailInfoViewModel: DetailInfoViewModel
    private var fragmentBinding: FragmentItemMovieDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailInfoViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DetailInfoViewModel::class.java)
        fragmentBinding = FragmentItemMovieDetailBinding.inflate(inflater, container, false)

        detailInfoViewModel.getMovieDetail(movieId).observe(viewLifecycleOwner, Observer {
            fragmentBinding!!.tvImdbId.text = it.imdbId
            fragmentBinding!!.tvMovieTitle.text = it.movieTitle
            fragmentBinding!!.tvMovieTagLine.text = it.tagline
            fragmentBinding!!.tvVote.text =
                getString(R.string.vote_value, it.voteAverage.toString())
            fragmentBinding!!.tvDescription.text =
                Html.fromHtml(it.overview, Html.FROM_HTML_MODE_COMPACT)
            val url = BuildConfig.PREFIX_URL_IMG + it.backdropPath
            Glide.with(requireActivity()).load(url).into(fragmentBinding!!.ivMoviePoster)
        })
        return fragmentBinding!!.root
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}