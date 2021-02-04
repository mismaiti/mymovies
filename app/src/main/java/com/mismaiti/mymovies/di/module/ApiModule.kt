package com.mismaiti.mymovies.di.module

import com.mismaiti.mymovies.api.MovieApi
import com.mismaiti.mymovies.service.MovieService
import dagger.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "api_key"
private const val IMDB_APIKEY = "6157533b0b8f89a9a85da30c921a903f"
private const val LANG = "language"
private const val LANG_VALUE = "en-US"
private const val PAGE = "page"


@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor {
            val request = it.request()
            val url = request.url().newBuilder()
                .addQueryParameter(API_KEY, IMDB_APIKEY)
                .addQueryParameter(LANG, LANG_VALUE)
                .addQueryParameter(PAGE, "1")
                .build()
            it.proceed(request.newBuilder().url(url).build())
        }
        client.connectTimeout(20, TimeUnit.SECONDS)
        client.readTimeout(20,TimeUnit.SECONDS)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)

    @Provides
    @Singleton
    fun provideMovieService(movieApi: MovieApi) : MovieService {
        return MovieService(movieApi)
    }
}
