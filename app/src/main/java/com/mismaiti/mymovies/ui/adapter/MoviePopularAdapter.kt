package com.mismaiti.mymovies.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mismaiti.mymovies.BuildConfig
import com.mismaiti.mymovies.api.dao.MovieDao
import com.mismaiti.mymovies.databinding.ItemMoviePopularBinding
import com.mismaiti.mymovies.databinding.ItemMovieUpcomingBinding
import com.mismaiti.mymovies.ui.dashboard.DashboardFragmentDirections

class MoviePopularAdapter(private val context: Context?, private val listMovie: List<MovieDao>?) :
    RecyclerView.Adapter<PopularMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        val binding = ItemMoviePopularBinding.inflate(LayoutInflater.from(context), parent, false)
        return PopularMovieViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        holder.onBind(listMovie?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return when (listMovie?.size) {
            null -> 0
            else -> listMovie.size
        }
    }
}

class PopularMovieViewHolder(val binding: ItemMoviePopularBinding, val context: Context?)
    : RecyclerView.ViewHolder(binding.root) {

    lateinit var navController: NavController

    fun onBind(movieDao: MovieDao) {
        val imageUrl = BuildConfig.PREFIX_URL_IMG + movieDao.backdropPath
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(35))
        Glide.with(context).load(imageUrl).apply(requestOptions)
            .into(binding.ivMoviePoster)

        binding.tvMovieTitle.text = movieDao.movieTitle
        binding.root.setOnClickListener {
            val action = DashboardFragmentDirections
                .actionNavigationDashboardToNavigationMovieDetail(movieDao.movieId!!)
            navController = Navigation.findNavController(it)
            navController.navigate(action)
        }
    }
}
