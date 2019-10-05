package com.example.mvidemo

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val BASE_URL = "https://customproject-6a04e.firebaseio.com/"

val appModule = module {
        factory  {
            ProfilePresenter(get())
        }
    }

val networkModule = module {
    factory { provideGson() }
    factory { provideHttpLoggingInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideApiInterface(get()) }
    single { provideRetrofit(get(),get()) }
}


fun provideGson() =
    GsonBuilder()
        .setLenient()
        .create()

fun provideHttpLoggingInterceptor() =
    HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)



fun provideOkHttpClient(interceptor: HttpLoggingInterceptor) =
       OkHttpClient
           .Builder()
           .addInterceptor(interceptor)
           .readTimeout(60, TimeUnit.SECONDS)
           .writeTimeout(60, TimeUnit.SECONDS)
           .connectTimeout(60, TimeUnit.SECONDS)
           .build()

fun provideRetrofit(gson: Gson, client: OkHttpClient) =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

fun provideApiInterface(retrofit: Retrofit) = retrofit.create(Webservice::class.java)