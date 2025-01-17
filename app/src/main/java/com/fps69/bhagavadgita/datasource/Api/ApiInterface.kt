package com.fps69.bhagavadgita.datasource.Api

import com.fps69.bhagavadgita.modle.ChaptersItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiInterface {


    @Headers(
        "Accept:application/json",
        "x-rapidapi-host:bhagavad-gita3.p.rapidapi.com",
        "x-rapidapi-key:12b8d9d056msh24d82e051760b03p16414fjsndc2807d26c04"
    )
    @GET("/v2/chapters/")
    fun getAllChapters(): Call<List<ChaptersItem>>
}