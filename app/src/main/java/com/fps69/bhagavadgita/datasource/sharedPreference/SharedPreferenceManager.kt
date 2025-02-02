package com.fps69.bhagavadgita.datasource.sharedPreference

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(val context: Context){

    val sharedPreferenceChapters: SharedPreferences by lazy {
        context.getSharedPreferences("saved_chapter", Context.MODE_PRIVATE)
    }


    fun getAllSavedChaptersFromSharedPreference() : Set<String>{
        return sharedPreferenceChapters.all.keys
    }


    fun putSavedChapterInSharedPreference(key: String, value: Int){
        sharedPreferenceChapters.edit().putInt(key,value).apply()
    }


    fun deleteSavedChapterFromSharedPreference(key:String ){
        sharedPreferenceChapters.edit().remove(key).apply()
    }






    val sharedPreferenceVerses: SharedPreferences by lazy {
        context.getSharedPreferences("Saved_verses", Context.MODE_PRIVATE)
    }

    fun getAllSaveVersesFromSharedPreference() : Set<String>{
        return sharedPreferenceVerses.all.keys
    }


    fun putSaveVersesInSharedPreference(key: String, value: Int){
        sharedPreferenceVerses.edit().putInt(key, value).apply()
    }


    fun deleteSaveVersesFromSharedPreference(key:String ){
        sharedPreferenceVerses.edit().remove(key).apply()
    }


}