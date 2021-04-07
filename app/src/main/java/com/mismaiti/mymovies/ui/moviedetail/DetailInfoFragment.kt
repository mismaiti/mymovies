package com.mismaiti.mymovies.ui.moviedetail

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mismaiti.mymovies.BuildConfig
import com.mismaiti.mymovies.R
import com.mismaiti.mymovies.databinding.FragmentItemMovieDetailBinding
import com.mismaiti.mymovies.util.reset
import com.mismaiti.mymovies.util.set
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DetailInfoFragment(private val movieId: Long) : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var detailInfoViewModel: DetailInfoViewModel
    lateinit var fragmentBinding: FragmentItemMovieDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailInfoViewModel =
            ViewModelProvider(this, viewModelFactory).get(DetailInfoViewModel::class.java)
        fragmentBinding = FragmentItemMovieDetailBinding.inflate(inflater, container, false)

        detailInfoViewModel.getMovieDetail(movieId).observe(viewLifecycleOwner, Observer {
            it.apply {
                with(fragmentBinding) {
                    tvImdbId.set(imdbId)
                    tvMovieTitle.set(movieTitle)
                    tvMovieTagLine.set(tagline)
                    tvVote.set(getString(R.string.vote_value, voteAverage.toString()))
                    tvDescription.set(Html.fromHtml(overview, Html.FROM_HTML_MODE_COMPACT).toString())
                    val url = BuildConfig.PREFIX_URL_IMG + backdropPath
                    Glide.with(requireActivity()).load(url).into(ivMoviePoster)
                }
            }
        })
        return fragmentBinding.root
    }

    override fun onDestroyView() {
        fragmentBinding.reset()
        super.onDestroyView()
    }
}


fun FragmentItemMovieDetailBinding.reset() {
    tvMovieTagLine.reset()
    tvImdbId.reset()
    tvMovieTitle.reset()
    tvDescription.reset()
    tvDescriptionLabel.reset()
    tvImdbIdLabel.reset()
    tvVote.reset()
    tvVoteLabel.reset()
}
