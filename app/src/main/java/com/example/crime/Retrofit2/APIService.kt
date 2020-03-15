package com.example.crime.Retrofit2
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*


interface ApiService
{
    /*--методы для получения данных--*/
    @GET("/posts/{id}")
    fun getuser(@Path("id") username:Int): Call<GitHubuser>
    @GET("/posts/")
    fun getuser1(): Call<List<GitHubuser>>
}