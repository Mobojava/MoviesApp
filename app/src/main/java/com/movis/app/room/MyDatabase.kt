package com.movis.app.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 2, exportSchema = false, entities = [MoviesTable::class])
abstract class MyDatabase() : RoomDatabase() {


    abstract val moviesDao: MoviesDao

    companion object {
        private var database: MyDatabase? = null
        fun getDatabase(context: Context): MyDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "MyDatabase.dp"
                )
                   // .fallbackToDestructiveMigration() // دیتابیس قبلی پاک میشه

                    .allowMainThreadQueries()
                    .build()
            }
            return database!!
        }
    }

}