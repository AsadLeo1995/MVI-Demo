package com.example.mvidemo

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WebServiceFactory {
    companion object {
        private const val BASE_URL = "https://customproject-6a04e.firebaseio.com/"

        fun getInstance() : ApiInterface{
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }
}