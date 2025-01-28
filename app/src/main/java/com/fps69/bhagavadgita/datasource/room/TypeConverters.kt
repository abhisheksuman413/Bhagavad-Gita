package com.fps69.bhagavadgita.datasource.room

import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverters {

    @TypeConverters
    fun fromListToString(list:List<String>): String{
        return Gson().toJson(list)
    }

    @TypeConverters
    fun fromStringToList(string: String): List<String>{
        return Gson().fromJson(string, object : TypeToken<List<String>>(){}.type)
    }



}