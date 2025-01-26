package com.fps69.bhagavadgita.datasource.Api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ApiUtilities {


    // Use of interseptor

    val headers = mapOf<String, String>(

        "Accept" to "application/json",
        "x-rapidapi-host" to "bhagavad-gita3.p.rapidapi.com",
        "x-rapidapi-key" to "12b8d9d056msh24d82e051760b03p16414fjsndc2807d26c04"
    )


    val clint = OkHttpClient.Builder().apply {


        connectTimeout(30, TimeUnit.SECONDS)  // Increase connection timeout
        readTimeout(30, TimeUnit.SECONDS)     // Increase read timeout
        writeTimeout(30, TimeUnit.SECONDS)

        addInterceptor { chain ->

            val newRequest = chain.request().newBuilder().apply {
                headers.forEach { key, value -> addHeader(key, value) }
            }.build()
            chain.proceed(newRequest)
        }
    }.build()


    val api: ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl("https://bhagavad-gita3.p.rapidapi.com")
            .client(clint)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}