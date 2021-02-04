package com.mismaiti.mymovies.di.module

import com.mismaiti.mymovies.MainActivity
import com.mismaiti.mymovies.ui.dashboard.DashboardFragment
import com.mismaiti.mymovies.ui.introduction.IntroductionFragment
import com.mismaiti.mymovies.ui.moviedetail.DetailCommentFragment
import com.mismaiti.mymovies.ui.moviedetail.DetailInfoFragment
import com.mismaiti.mymovies.ui.moviedetail.MovieDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingModule {

    @ContributesAndroidInjector
    abstract fun provideDashboardFragment() : DashboardFragment

    @ContributesAndroidInjector
    abstract fun provideMainActivity() : MainActivity

    @ContributesAndroidInjector
    abstract fun provideMovieDetailFragment() : MovieDetailFragment

    @ContributesAndroidInjector
    abstract fun provideDetailInfoFragment() : DetailInfoFragment

    @ContributesAndroidInjector
    abstract fun provideDetailCommentFragment() : DetailCommentFragment

    @ContributesAndroidInjector
    abstract fun provideIntroductionFragment() : IntroductionFragment

}