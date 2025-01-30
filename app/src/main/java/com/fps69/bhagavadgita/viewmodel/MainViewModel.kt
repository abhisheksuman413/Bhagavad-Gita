package com.fps69.bhagavadgita.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.fps69.bhagavadgita.datasource.room.AppDatabase
import com.fps69.bhagavadgita.datasource.room.SavedChapters
import com.fps69.bhagavadgita.modle.ChaptersItem
import com.fps69.bhagavadgita.modle.VersesItem
import com.fps69.bhagavadgita.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(application: Application) : AndroidViewModel(application) {


    val savedChaptersDao = AppDatabase.getDatabaseInstance(application)?.savedChapterDao()

    val appRepository = AppRepository(savedChaptersDao!!)

    fun getAllChapters() : Flow<List<ChaptersItem>>{
        return appRepository.getAllChapters()
    }


    fun getVerses(chapterNumber : Int ) : Flow<List<VersesItem>> = appRepository.getVerses(chapterNumber)



    fun getVerseDetails(chapterNumber : Int, verseNumber : Int ) : Flow<VersesItem> =  appRepository.getVerseDetails(chapterNumber, verseNumber)




    // Saved Chapters


    suspend fun insertChapters(chapter: SavedChapters) = appRepository.insertChapter(chapter)


}