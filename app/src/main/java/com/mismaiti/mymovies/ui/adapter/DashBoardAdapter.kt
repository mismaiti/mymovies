package com.mismaiti.mymovies.ui.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mismaiti.mymovies.BuildConfig
import com.mismaiti.mymovies.R
import com.mismaiti.mymovies.api.dao.MovieDao
import com.mismaiti.mymovies.databinding.ItemDashboardSectionTitleBinding
import com.mismaiti.mymovies.databinding.ItemListMoviePopularBinding
import com.mismaiti.mymovies.databinding.ItemMovieUpcomingBinding
import com.mismaiti.mymovies.model.UIModel
import com.mismaiti.mymovies.ui.dashboard.DashboardFragmentDirections
import com.mismaiti.mymovies.ui.dashboard.DashboardViewModel
import com.mismaiti.mymovies.util.GridItemDecoration
import java.util.*

class DashboardAdapter(private val context: Context?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listUIModel: ArrayList<UIModel> = ArrayList()

    fun submitData(list: List<UIModel>) {
        if (list.size < 4) return
        listUIModel.clear()
        for (item in list) {
            when (item) {
                is UIModel.UpcomingModel
                -> {
                    for (itemMovie in item.listUpcoming) {
                        listUIModel.add(UIModel.UpcomingItem(itemMovie))
                    }
                }
                else -> listUIModel.add(item)
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val popularViewHolder = PopularMovieListViewHolder(
            ItemListMoviePopularBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ), context
        )
        return when (viewType) {
            R.layout.item_list_movie_popular -> popularViewHolder

            R.layout.item_movie_upcoming -> UpcomingMovieViewHolder(
                ItemMovieUpcomingBinding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
                )
            )
            else -> SectionMovieViewHolder(
                ItemDashboardSectionTitleBinding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (listUIModel[position]) {
            is UIModel.PopularModel -> R.layout.item_list_movie_popular
            is UIModel.UpcomingItem -> R.layout.item_movie_upcoming
            else -> R.layout.item_dashboard_section_title
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = listUIModel[position]) {
            is UIModel.PopularModel -> (holder as PopularMovieListViewHolder).onBind(item.listPopular)
            is UIModel.UpcomingItem -> (holder as UpcomingMovieViewHolder).onBind(item.movieDao, context)
            is UIModel.SectionHeaderModel -> (holder as SectionMovieViewHolder).onBind(item.sectionName)
        }
    }

    override fun getItemCount(): Int = listUIModel.size

}

class PopularMovieListViewHolder(
    private val binding: ItemListMoviePopularBinding,
    val context: Context?
    ) : RecyclerView.ViewHolder(binding.root) {

    private var pagerSnapHelper: PagerSnapHelper? = null
    private var gridItemDecoration: GridItemDecoration? = null
    var timer: Timer? = null
    private var moviePopularAdapter: MoviePopularAdapter? = null
    private var listMovie: List<MovieDao>? = null

    fun onBind(listMovie: List<MovieDao>) {
        this.listMovie = listMovie
        with(binding.rvListPopularMoview) {
            layoutManager =
                GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
            moviePopularAdapter = MoviePopularAdapter(context, listMovie)
            adapter = moviePopularAdapter
            if (pagerSnapHelper == null) {
                pagerSnapHelper = PagerSnapHelper()
                pagerSnapHelper!!.attachToRecyclerView(this)
            }
            if (gridItemDecoration == null) {
                gridItemDecoration = GridItemDecoration(
                    context.resources.getDimensionPixelSize(R.dimen.item_list_margin),
                    context.resources.getDimensionPixelSize(R.dimen.item_out_list_margin)
                )
                addItemDecoration(
                    gridItemDecoration!!
                )
            }
        }
        if (timer == null) {
            initAutoScroll()
        }
    }

    private fun initAutoScroll() {
        timer = Timer()
        with(binding.rvListPopularMoview) {
            post {
                var position = 0
                var isEnd = false
                    timer!!.scheduleAtFixedRate(object : TimerTask() {
                        override fun run() {
                            if (position == listMovie?.size?.minus(1)) {
                                isEnd = true
                            } else if (position == 0) {
                                isEnd = false
                            }

                            if (!isEnd) {
                                position++
                            } else {
                                position--
                            }
                            val handler = Handler(itemView.context.mainLooper)
                            handler.post {
                                binding.rvListPopularMoview.smoothScrollToPosition(position)
                            }
                        }

                    }, 2000, 4000)
            }
        }
    }
}

class UpcomingMovieViewHolder(private val binding: ItemMovieUpcomingBinding) :
    RecyclerView.ViewHolder(binding.root) {

    lateinit var navController: NavController

    fun onBind(movieDao: MovieDao?, context: Context?) {
        val imageUrl = BuildConfig.PREFIX_URL_IMG + movieDao?.posterPath
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(25))
        Glide.with(context).load(imageUrl).apply(requestOptions).into(binding.ivMoviePoster)
        binding.tvMovieTitle.text = movieDao?.movieTitle
        binding.root.setOnClickListener {
            val action = DashboardFragmentDirections
                .actionNavigationDashboardToNavigationMovieDetail(movieDao?.movieId!!)
            navController = Navigation.findNavController(it)
            navController.navigate(action)
        }
    }
}

class SectionMovieViewHolder(private val binding: ItemDashboardSectionTitleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(section: String) {
        when (section) {
            "Upcoming Movies" -> binding.vMovieSectionHighlight.visibility = View.VISIBLE
            else -> binding.vMovieSectionHighlight.visibility = View.GONE
        }

        binding.tvMovieSectionTitle.text = section
    }
}