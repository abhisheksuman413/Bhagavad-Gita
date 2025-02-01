package com.fps69.bhagavadgita.datasource.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fps69.bhagavadgita.modle.Commentary
import com.fps69.bhagavadgita.modle.Translation

@Entity(tableName = "saved_chapters")
@TypeConverters(myConverterssss::class) // Ensure type converters are applied
    data class SavedChapters(
        val chapter_number: Int,
        val chapter_summary: String,
        val chapter_summary_hindi: String,
        @PrimaryKey
        val id: Int,
        val name: String,
        val name_meaning: String,
        val name_translated: String,
        val name_transliterated: String,
        val slug: String,
        val verses_count: Int,
        val verses : List<String>
    )

@Entity(tableName = "saved_verses")
@TypeConverters(myConverterssss::class)
data class SavedVerses(
    val chapter_number: Int,
    val commentaries: List<Commentary>,
    @PrimaryKey
    val id: Int,
    val slug: String,
    val text: String,
    val translations: List<Translation>,
    val transliteration: String,
    val verse_number: Int,
    val word_meanings: String
)