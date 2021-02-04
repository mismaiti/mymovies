package com.mismaiti.mymovies.model

import com.mismaiti.mymovies.api.dao.MovieDao

sealed class UIModel {

    class PopularModel(var listPopular: List<MovieDao>): UIModel()

    class SectionHeaderModel(var sectionName: String): UIModel()

    class UpcomingModel(var listUpcoming: List<MovieDao>): UIModel()

    class UpcomingItem(var movieDao: MovieDao): UIModel()
}
