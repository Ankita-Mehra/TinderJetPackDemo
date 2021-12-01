package com.softradix.tinderjetpackdemo.retrofit

import android.content.Context
import com.softradix.tinderjetpackdemo.app.AppClass
import com.softradix.tinderjetpackdemo.data.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiHelper {

    @Singleton
    @Provides
    fun provideRetrofitClient(): ApiInterface {
        var okHttpClient: OkHttpClient? = null

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .readTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES).build()

        return Retrofit.Builder().baseUrl("https://tinder.softradixtechnologies.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build().create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun getUserRepository(apiInterface: ApiInterface) = UserRepository(apiInterface)


    @Singleton  //only work with ApplicationComponent
    @Provides
    fun provideApplication(@ApplicationContext app: Context): AppClass {
        return app as AppClass
    }


}