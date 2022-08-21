package com.vmware.nparui.gitpr.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val PR_RETROFIT_URL = "https://api.github.com"

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @RetrofitPullRequest
    @Provides
    fun providesRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(PR_RETROFIT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @DispatcherIO
    @Provides
    fun providesDispatcher() : CoroutineDispatcher {
        return Dispatchers.IO
    }

}