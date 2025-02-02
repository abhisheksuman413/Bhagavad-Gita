package com.fps69.bhagavadgita.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.fps69.bhagavadgita.datasource.room.AppDatabase
import com.fps69.bhagavadgita.datasource.room.SavedChapters
import com.fps69.bhagavadgita.datasource.room.SavedVerses
import com.fps69.bhagavadgita.datasource.sharedPreference.SharedPreferenceManager
import com.fps69.bhagavadgita.modle.ChaptersItem
import com.fps69.bhagavadgita.modle.VersesItem
import com.fps69.bhagavadgita.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(application: Application) : AndroidViewModel(application) {


    val savedChaptersDao = AppDatabase.getDatabaseInstance(application)?.savedChapterDao()
    val savedVersesDao = AppDatabase.getDatabaseInstance(application)?.savedVersesDao()

    val sharedPreferenceManager = SharedPreferenceManager(application)

    val appRepository = AppRepository(savedChaptersDao!!, savedVersesDao!!,sharedPreferenceManager)

    fun getAllChapters() : Flow<List<ChaptersItem>>{
        return appRepository.getAllChapters()
    }


    fun getVerses(chapterNumber : Int ) : Flow<List<VersesItem>> = appRepository.getVerses(chapterNumber)



    fun getVerseDetails(chapterNumber : Int, verseNumber : Int ) : Flow<VersesItem> =  appRepository.getVerseDetails(chapterNumber, verseNumber)




    // Saved Chapters


    suspend fun insertChapters(chapter: SavedChapters) = appRepository.insertChapter(chapter)

    fun getSavedChapters() : LiveData<List<SavedChapters>> = appRepository.getSavedChapters()

    fun getAParticularChapter(chapterNumber: Int): LiveData<SavedChapters> = appRepository.getAParticularChapter(chapterNumber)

    suspend fun deleteChapter(id : Int)= appRepository.deleteChapter(id)



    suspend fun insertEnglishVerse(verseInEnglish: SavedVerses) = appRepository.insertEnglishVerse(verseInEnglish)

    fun getAllEnglishVerses():LiveData<List<SavedVerses>> = appRepository.getAllEnglishVerses()

    fun getParticularVerse(chapterNumber: Int, verseNumber:Int): LiveData<SavedVerses> = appRepository.getParticularVerse(chapterNumber,verseNumber)

    suspend fun deleteAParticularVerse(chapterNumber :Int , verseNumber:Int) = appRepository.deleteAParticularVerse(chapterNumber,verseNumber)





    // Manage Chapters in Shared Preference

    fun getAllSavedChaptersFromSharedPreference() : Set<String> = appRepository.getAllSavedChaptersFromSharedPreference()


    fun putSavedChapterInSharedPreference(key: String, value: Int)= appRepository.putSavedChapterInSharedPreference(key, value)


    fun deleteSavedChapterFromSharedPreference(key:String )=appRepository.deleteSavedChapterFromSharedPreference(key)




    // Manage Verses in Shared Preference

    fun getAllSaveVersesFromSharedPreference() : Set<String> = appRepository.getAllSaveVersesFromSharedPreference()

    fun putSaveVersesInSharedPreference(key: String, value: Int)= appRepository.putSaveVersesInSharedPreference(key,value)

    fun deleteSaveVersesFromSharedPreference(key:String )=appRepository.deleteSaveVersesFromSharedPreference(key)


}