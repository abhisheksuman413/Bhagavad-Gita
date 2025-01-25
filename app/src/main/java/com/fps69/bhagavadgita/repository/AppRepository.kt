package com.fps69.bhagavadgita.repository

import com.fps69.bhagavadgita.datasource.Api.ApiUtilities
import com.fps69.bhagavadgita.modle.ChaptersItem
import com.fps69.bhagavadgita.modle.VersesItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository {


//    fun getAllChapters(): Flow<List<ChaptersItem>> = callbackFlow {
//
//    }
// Upper wale ko ase likhe hai


    fun getAllChapters(): Flow<List<ChaptersItem>> {
        return callbackFlow {

            val callBAck = object : Callback<List<ChaptersItem>> {
                override fun onResponse(
                    call: Call<List<ChaptersItem>>,
                    response: Response<List<ChaptersItem>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        trySend(response.body()!!)
                        close()
                    }
                }

                override fun onFailure(call: Call<List<ChaptersItem>>, t: Throwable) {
                    close(t)
                }

            }


            ApiUtilities.api.getAllChapters().enqueue(callBAck)

            awaitClose { }
        }

    }


    fun getVerses(chapterNumber : Int ): Flow<List<VersesItem>> = callbackFlow{
        val callBack  = object : Callback<List<VersesItem>>{
            override fun onResponse(call: Call<List<VersesItem>>, response: Response<List<VersesItem>>) {

                if(response.isSuccessful && response.body() != null ){
                    trySend(response.body()!!)
                    close()
                }

            }

            override fun onFailure(call: Call<List<VersesItem>>, t: Throwable) {
                close(t)
            }

        }


        ApiUtilities.api.getVerses(chapterNumber).enqueue(callBack)
        awaitClose{}
    }



    fun getVerseDetails(chapterNumber : Int, verseNumber : Int) : Flow<VersesItem> = callbackFlow {

        val callBack = object : Callback<VersesItem>{
            override fun onResponse(call: Call<VersesItem>, response: Response<VersesItem>) {
                if(response.isSuccessful && response.body() != null){
                    trySend(response.body()!!)
                    close()
                }
            }

            override fun onFailure(call: Call<VersesItem>, t: Throwable) {
                close(t)
            }

        }


        ApiUtilities.api.getVerseDetails(chapterNumber, verseNumber).enqueue(callBack)
        awaitClose{}

    }

}