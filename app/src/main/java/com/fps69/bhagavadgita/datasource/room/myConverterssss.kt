package com.fps69.bhagavadgita.datasource.room

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.fps69.bhagavadgita.modle.Commentary
import com.fps69.bhagavadgita.modle.Translation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class myConverterssss {

    @TypeConverter
    fun fromListToString(list:List<String>): String{
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToList(string: String): List<String>{
        return Gson().fromJson(string, object : TypeToken<List<String>>(){}.type)
    }


    @TypeConverter
    fun commentaryToString(commentaries: List<Commentary>): String {
        return Gson().toJson(commentaries)
    }

//    @TypeConverter
//    fun stringToCommentary(string: String): List<Commentary>{
//        return Gson().fromJson(string, object :TypeToken<List<String>>(){}.type)
//    }

    @TypeConverter
    fun translationToString(translation: List<Translation>): String {
        return Gson().toJson(translation)
    }

//    @TypeConverter
//    fun stringToTranslation(string: String): List<Translation>{
//        return Gson().fromJson(string, object :TypeToken<List<String>>(){}.type)
//    }



    @TypeConverter
    fun stringToCommentary(string: String?): List<Commentary> {
        if (string.isNullOrEmpty()) return emptyList()  // Handle null or empty case

        return try {
            Gson().fromJson(string, object : TypeToken<List<Commentary>>() {}.type)
        } catch (e: Exception) {
            emptyList() // If JSON is invalid, return an empty list
        }
    }

    @TypeConverter
    fun stringToTranslation(string: String?): List<Translation> {
        if (string.isNullOrEmpty()) return emptyList()

        return try {
            Gson().fromJson(string, object : TypeToken<List<Translation>>() {}.type)
        } catch (e: Exception) {
            emptyList()
        }
    }


}