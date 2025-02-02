package com.fps69.bhagavadgita.repository

import androidx.lifecycle.LiveData
import com.fps69.bhagavadgita.datasource.Api.ApiUtilities
import com.fps69.bhagavadgita.datasource.room.SavedChapterDao
import com.fps69.bhagavadgita.datasource.room.SavedChapters
import com.fps69.bhagavadgita.datasource.room.SavedVerses
import com.fps69.bhagavadgita.datasource.room.SavedVersesDao
import com.fps69.bhagavadgita.datasource.sharedPreference.SharedPreferenceManager
import com.fps69.bhagavadgita.modle.ChaptersItem
import com.fps69.bhagavadgita.modle.VersesItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository(val savedChaptersDao: SavedChapterDao, val savedVersesDao: SavedVersesDao, val sharedPreferencesManager: SharedPreferenceManager){


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


    suspend fun insertChapter(chapter: SavedChapters) = savedChaptersDao.insertChapter(chapter)


    fun getSavedChapters() : LiveData<List<SavedChapters>> = savedChaptersDao.getSavedChapters()


    fun getAParticularChapter(chapterNumber: Int): LiveData<SavedChapters> = savedChaptersDao.getAParticularChapter(chapterNumber)

    suspend fun deleteChapter(id : Int)= savedChaptersDao.deleteChapter(id)





    suspend fun insertEnglishVerse(verseInEnglish: SavedVerses) = savedVersesDao?.insertEnglishVerse(verseInEnglish)

    fun getAllEnglishVerses():LiveData<List<SavedVerses>> = savedVersesDao.getAllEnglishVerses()

    fun getParticularVerse(chapterNumber: Int, verseNumber:Int): LiveData<SavedVerses> = savedVersesDao.getParticularVerse(chapterNumber,verseNumber)

    suspend fun deleteAParticularVerse(chapterNumber :Int , verseNumber:Int)= savedVersesDao.deleteAParticularVerse(chapterNumber,verseNumber)




    // Manage Chapters in Shared Preference

    fun getAllSavedChaptersFromSharedPreference() : Set<String> = sharedPreferencesManager.getAllSavedChaptersFromSharedPreference()


    fun putSavedChapterInSharedPreference(key: String, value: Int)= sharedPreferencesManager.putSavedChapterInSharedPreference(key, value)


    fun deleteSavedChapterFromSharedPreference(key:String )=sharedPreferencesManager.deleteSavedChapterFromSharedPreference(key)



    // Manage Verses in Shared Preference

    fun getAllSaveVersesFromSharedPreference() : Set<String> = sharedPreferencesManager.getAllSaveVersesFromSharedPreference()

    fun putSaveVersesInSharedPreference(key: String, value: Int)= sharedPreferencesManager.putSaveVersesInSharedPreference(key,value)

    fun deleteSaveVersesFromSharedPreference(key:String )=sharedPreferencesManager.deleteSaveVersesFromSharedPreference(key)
}