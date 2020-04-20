package com.example.mvidemo

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface Webservice {
    @GET("/User/{user_id}.json")
    fun getMyProfile(@Path("user_id") userId: String) : Observable<User> //User
}