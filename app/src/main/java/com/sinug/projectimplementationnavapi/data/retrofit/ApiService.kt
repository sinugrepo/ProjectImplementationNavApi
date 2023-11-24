package com.sinug.projectimplementationnavapi.data.retrofit

import com.sinug.projectimplementationnavapi.data.response.DetailUserResponse
import com.sinug.projectimplementationnavapi.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getProfile(
        @Query("q") username: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollower(@Path("username") username: String): Call<List<DetailUserResponse>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<DetailUserResponse>>
}