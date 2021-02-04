package com.mismaiti.mymovies.service

import com.mismaiti.mymovies.api.MovieApi
import com.mismaiti.mymovies.api.dao.MovieDao
import com.mismaiti.mymovies.api.dao.MovieResult
import com.mismaiti.mymovies.api.dao.MovieReviewResult
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MovieService(private val movieApi: MovieApi) {

    fun getPopularMovie() : Observable<MovieResult> =
        movieApi.fetchPopularMovies().subscribeOn(Schedulers.io())

    fun getUpcomingMovies() : Observable<MovieResult> =
        movieApi.fetchUpComingMovies().subscribeOn(Schedulers.io())

    fun getMovieDetail(movieId: Long) : Observable<MovieDao> =
        movieApi.fetchMovieDetail(movieId).subscribeOn(Schedulers.io())

    fun getMovieReview(movieId: Long) : Observable<MovieReviewResult> =
        movieApi.fetchMovieReview(movieId).subscribeOn(Schedulers.io())
}