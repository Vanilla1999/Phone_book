package com.example.crime.Retrofit2

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose



public class GitHubuser {

    @SerializedName("userId")
    @Expose
    private val userId: Int = 0
    @SerializedName("id")
    @Expose
    private val id: Int = 0
    @SerializedName("title")
    @Expose
    private val title: String? = null
    @SerializedName("body")
    @Expose
    private val body: String? = null

    fun getUserId():Int {
        return userId
    }

    fun getId():Int {
        return id
    }

    fun getTitle():String {
        return title!!
    }

    fun getBody():String {
        return body!!
    }

}