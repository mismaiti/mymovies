package com.mismaiti.mymovies.api

import com.mismaiti.mymovies.api.dao.MovieDao
import com.mismaiti.mymovies.api.dao.MovieResult
import com.mismaiti.mymovies.api.dao.MovieReviewResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movie/popular")
    fun fetchPopularMovies() : Observable<MovieResult>

    @GET("movie/upcoming")
    fun fetchUpComingMovies() : Observable<MovieResult>

    @GET("movie/{movie_id}")
    fun fetchMovieDetail(@Path("movie_id") long: Long) : Observable<MovieDao>

    @GET("movie/{movie_id}/reviews")
    fun fetchMovieReview (@Path("movie_id") long: Long) : Observable<MovieReviewResult>
}