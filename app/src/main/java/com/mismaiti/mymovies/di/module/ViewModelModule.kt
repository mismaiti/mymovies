package com.mismaiti.mymovies.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mismaiti.mymovies.di.annotation.ViewModelKey
import com.mismaiti.mymovies.ui.dashboard.DashboardViewModel
import com.mismaiti.mymovies.ui.moviedetail.DetailInfoViewModel
import com.mismaiti.mymovies.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ApiModule::class])
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindDashboardViewModel(viewModel: DashboardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailInfoViewModel::class)
    abstract fun bindMovieDetailViewModel(viewModel: DetailInfoViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}