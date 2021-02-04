package com.mismaiti.mymovies.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mismaiti.mymovies.api.dao.MovieResult
import com.mismaiti.mymovies.service.MovieService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DashboardViewModel @Inject constructor(private val movieService: MovieService) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun getListMoviePopular(): LiveData<MovieResult> =
        MutableLiveData<MovieResult>().apply {
            compositeDisposable.addAll(
                movieService.getPopularMovie().observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        value = it
                    }, {
                        it.stackTrace
                    })
            )
        }

    fun getListMovieUpComing(): LiveData<MovieResult> =
        MutableLiveData<MovieResult>().apply {
            compositeDisposable.addAll(
                movieService.getUpcomingMovies().observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        value = it
                    }, {
                        it.stackTrace
                    })
            )
        }
}