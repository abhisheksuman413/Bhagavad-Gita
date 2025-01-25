package com.fps69.bhagavadgita.viewmodel

import androidx.lifecycle.ViewModel
import com.fps69.bhagavadgita.modle.ChaptersItem
import com.fps69.bhagavadgita.modle.VersesItem
import com.fps69.bhagavadgita.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {



    val appRepository = AppRepository()

    fun getAllChapters() : Flow<List<ChaptersItem>>{
        return appRepository.getAllChapters()
    }


    fun getVerses(chapterNumber : Int ) : Flow<List<VersesItem>> = appRepository.getVerses(chapterNumber)



    fun getVerseDetails(chapterNumber : Int, verseNumber : Int ) : Flow<VersesItem> =  appRepository.getVerseDetails(chapterNumber, verseNumber)

}