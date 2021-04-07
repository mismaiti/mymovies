package com.mismaiti.mymovies.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mismaiti.mymovies.R
import com.mismaiti.mymovies.databinding.FragmentDashboardBinding
import com.mismaiti.mymovies.model.UIModel
import com.mismaiti.mymovies.ui.adapter.DashboardAdapter
import com.mismaiti.mymovies.util.GridItemDecoration
import com.mismaiti.mymovies.util.getDayGreeting
import com.mismaiti.mymovies.util.set
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import kotlin.collections.ArrayList


class DashboardFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var dashboardViewModel: DashboardViewModel
    lateinit var fragmentBinding: FragmentDashboardBinding
    lateinit var dashboardAdapter: DashboardAdapter

    private var listUIModel: ArrayList<UIModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel =
            ViewModelProvider(this, viewModelFactory).get(DashboardViewModel::class.java)
        fragmentBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        dashboardAdapter = DashboardAdapter(context)

        with(fragmentBinding) {

            tvGreetingTitle.getDayGreeting()
            tvUsername.set(arguments?.getString("nickname"))

            with(rvPopularMovies) {
                val rvLayoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                layoutManager = rvLayoutManager
                adapter = dashboardAdapter
                addItemDecoration(GridItemDecoration(
                    context.resources.getDimensionPixelSize(R.dimen.item_list_margin),
                    context.resources.getDimensionPixelSize(R.dimen.item_out_list_margin)
                ))
                rvLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return when(position) {
                            in 0..2 -> 2
                            else -> 1
                        }
                    }
                }
            }
        }

        with(listUIModel) {
            if (isNotEmpty() && size == 4) {
                dashboardAdapter.submitData(this)
            }
            else {
                add(UIModel.SectionHeaderModel(getString(R.string.popular_movies)))
                add(UIModel.SectionHeaderModel(getString(R.string.upcoming_movies)))
                dashboardViewModel.apply {
                    getListMoviePopular().observe(viewLifecycleOwner, Observer {
                        add(1, UIModel.PopularModel(it.listMovieDao))
                        dashboardAdapter.submitData(this@with)
                    })

                    getListMovieUpComing().observe(viewLifecycleOwner, {
                        add(listUIModel.size, UIModel.UpcomingModel(it.listMovieDao))
                        dashboardAdapter.submitData(this@with)
                    })
                }
            }
        }

        return fragmentBinding.root
    }

    override fun onDestroyView() {
        fragmentBinding.reset()
        super.onDestroyView()
    }
}

fun FragmentDashboardBinding.reset() {
    rvPopularMovies.adapter = null
}
