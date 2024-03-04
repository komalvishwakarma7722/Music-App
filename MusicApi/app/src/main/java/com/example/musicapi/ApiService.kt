package com.example.musicapi


import com.example.musicapi.model.MyData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers(
        "X-RapidAPI-Key: 1ef5caa3d9msh6f5e88631c82631p173469jsnb06250ad2d8a",
    "X-RapidAPI-Host: deezerdevs-deezer.p.rapidapi.com"
    )
    @GET("search")
    fun getUserlist(
        @Query("q") musicCode:String
    ) : Call<MyData>
}