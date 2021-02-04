package com.mismaiti.mymovies.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mismaiti.mymovies.api.dao.MovieDao
import com.mismaiti.mymovies.api.dao.MovieReviewResult
import com.mismaiti.mymovies.service.MovieService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DetailInfoViewModel @Inject constructor(private val movieService: MovieService) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun getMovieDetail(movieId: Long) : LiveData<MovieDao> =
        MutableLiveData<MovieDao>().apply {
            compositeDisposable.addAll(
                movieService.getMovieDetail(movieId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                               value = it
                    }, {
                        it.stackTrace
                    })
            )
        }

    fun getListMovieComment(movieId: Long) : LiveData<MovieReviewResult> =
        MutableLiveData<MovieReviewResult>().apply {
            compositeDisposable.addAll(
                movieService.getMovieReview(movieId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                          value = it
                    }, {
                        it.stackTrace
                    })
            )
        }
}