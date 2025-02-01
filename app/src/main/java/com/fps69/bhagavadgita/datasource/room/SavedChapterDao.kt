package com.fps69.bhagavadgita.datasource.room


import androidx.lifecycle.LiveData
import androidx.room.Dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface SavedChapterDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapter(savedChapters: SavedChapters)

    @Query("SELECT * FROM saved_chapters")
    fun getSavedChapters() : LiveData<List<SavedChapters>>

    @Query("DELETE FROM saved_chapters WHERE id = :id")
    suspend fun deleteChapter(id : Int)

    @Query("SELECT * FROM saved_chapters WHERE chapter_number = :chapterNumber")
    fun getAParticularChapter(chapterNumber: Int): LiveData<SavedChapters>

}


@Dao
interface SavedVersesDao{


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnglishVerse(verseInEnglish: SavedVerses)


    @Query("SELECT * FROM saved_verses")
    fun getAllEnglishVerses():LiveData<List<SavedVerses>>

    @Query("SELECT * FROM saved_verses WHERE chapter_number=:chapterNumber AND verse_number=:verseNumber")
    fun getParticularVerse(chapterNumber: Int, verseNumber:Int): LiveData<SavedVerses>


    @Query("DELETE FROM saved_verses WHERE chapter_number = :chapterNumber AND verse_number=:verseNumber")
    suspend fun deleteAParticularVerse(chapterNumber :Int , verseNumber:Int)
}