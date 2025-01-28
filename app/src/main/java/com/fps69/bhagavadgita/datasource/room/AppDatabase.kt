package com.fps69.bhagavadgita.datasource.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [SavedChapters::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun savedChapterDao(): SavedChapterDao


    companion object {
        @Volatile
        var INSTANCE: AppDatabase? = null

        fun getDatabaseInstance(context: Context): AppDatabase? {
            val temINSTANCE = INSTANCE
            if (INSTANCE != null) {
                return temINSTANCE
            } else {
                synchronized(this) {
                    val roomDb =
                        Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase")
                            .build()

                    INSTANCE = roomDb

                    return roomDb
                }
            }
        }
    }

}