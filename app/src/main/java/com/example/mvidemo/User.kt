package com.example.mvidemo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class User {

    @SerializedName("user_id")
    @Expose
    var userId: Int? = null
    @SerializedName("user_image")
    @Expose
    var userImage: String? = null
    @SerializedName("user_name")
    @Expose
    var userName: String? = null

}