package com.mismaiti.mymovies.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.mismaiti.mymovies.R
import com.mismaiti.mymovies.databinding.FragmentDashboardBinding
import com.mismaiti.mymovies.model.UIModel
import com.mismaiti.mymovies.ui.adapter.DashboardAdapter
import com.mismaiti.mymovies.util.GridItemDecoration
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class DashboardFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var dashboardViewModel: DashboardViewModel

    private var listUIModel: ArrayList<UIModel> = ArrayList()
    private var fragmentBinding: FragmentDashboardBinding? = null
    private var dashboardAdapter: DashboardAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java)
        fragmentBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        fragmentBinding?.tvGreetingTitle?.text = getDayGreeting()
        fragmentBinding?.tvUsername?.text = arguments?.getString("nickname")

        with(fragmentBinding?.rvPopularMovies) {
            val rvLayoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            this?.layoutManager = rvLayoutManager
            this?.adapter = DashboardAdapter(context)
            dashboardAdapter = this?.adapter as DashboardAdapter
            this?.addItemDecoration(GridItemDecoration(
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
        if (listUIModel.isNotEmpty() && listUIModel.size == 4) {
            dashboardAdapter?.submitData(listUIModel)
        }
        else {
            listUIModel.add(UIModel.SectionHeaderModel(getString(R.string.popular_movies)))
            listUIModel.add(UIModel.SectionHeaderModel(getString(R.string.upcoming_movies)))

            dashboardViewModel.apply {
                getListMoviePopular().observe(viewLifecycleOwner, Observer {
                    listUIModel.add(1, UIModel.PopularModel(it.listMovieDao))
                    dashboardAdapter?.submitData(listUIModel)
                })

                getListMovieUpComing().observe(viewLifecycleOwner, {
                    listUIModel.add(listUIModel.size, UIModel.UpcomingModel(it.listMovieDao))
                    dashboardAdapter?.submitData(listUIModel)
                })
            }
        }

        return fragmentBinding!!.root
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    private fun getDayGreeting() : String {
        val c: Calendar = Calendar.getInstance()
        return when (c.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "Good Morning,"
            in 12..15 -> "Good Afternoon,"
            in 16..20 -> "Good Evening,"
            else -> "Good Night,"
        }
    }
}