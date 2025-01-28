package com.fps69.bhagavadgita.datasource.room

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface SavedChapterDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapter(chapter: SavedChapters)

    @Query("SELECT * FROM SavedChapters")
    suspend fun getSavedChapters() : LiveData<List<SavedChapters>>

    @Query("DELETE FROM SavedChapters WHERE id = :id")
    suspend fun deleteChapter(id : Int)

    @Query("SELECT * FROM SavedChapters WHERE chapter_number = :chapterNumber")
    suspend fun getAParticularChapter(chapterNumber: Int): LiveData<List<String>>




}