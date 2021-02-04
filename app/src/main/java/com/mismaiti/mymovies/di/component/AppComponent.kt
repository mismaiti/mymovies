package com.mismaiti.mymovies.di.component

import android.app.Application
import com.mismaiti.mymovies.MyApplication
import com.mismaiti.mymovies.di.module.BindingModule
import com.mismaiti.mymovies.di.module.AppModule
import com.mismaiti.mymovies.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ViewModelModule::class,
    BindingModule::class
])
interface AppComponent : AndroidInjector<MyApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    override fun inject(instance: MyApplication?)
}