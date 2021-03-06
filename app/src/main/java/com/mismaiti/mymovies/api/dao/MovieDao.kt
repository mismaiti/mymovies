package com.mismaiti.mymovies.api.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.annotations.SerializedName

data class MovieResult (
    @SerializedName("results")
    val listMovieDao: List<MovieDao>
)

data class MovieDao (
    @SerializedName("id")
    val movieId: Long?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("title")
    val movieTitle: String?,
    val tagline: String?,
    val overview: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("poster_path")
    val posterPath: String?
)

data class MovieReviewResult (
    @SerializedName("results")
    val results : List<MovieReviewDao>
)

data class MovieReviewDao (
    @SerializedName("author_details")
    val authorDetail: AuthorDao?,
    @SerializedName("content")
    val content: String?
)

data class AuthorDao (
    @SerializedName("username")
    val username: String?
)
