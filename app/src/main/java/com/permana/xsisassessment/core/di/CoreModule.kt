package com.permana.xsisassessment.core.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.permana.xsisassessment.core.data.api.repository.MovieRepository
import com.permana.xsisassessment.core.data.implementation.remote.MovieApi
import com.permana.xsisassessment.core.data.implementation.repository.MovieRepositoryImpl
import com.permana.xsisassessment.core.utils.AppExecutors
import com.permana.xsisassessment.core.utils.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(getChuckerInterceptor(get()))
            .addInterceptor(HeaderInterceptor())
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(MovieApi::class.java)
    }
}

fun getChuckerInterceptor(context: Context): ChuckerInterceptor {
    return ChuckerInterceptor.Builder(context).build()
}

val repositoryModule = module {
    factory { AppExecutors() }
    single<MovieRepository> { MovieRepositoryImpl(get()) }
}