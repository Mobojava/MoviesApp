package com.movis.app.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MoviesDao {

    @Insert
    fun insertMovies(moviesTable: MoviesTable)

    @Delete
    fun deletMovies(moviesTable: MoviesTable)

    @Query( "SELECT * FROM MoviesTable" )
    fun getAllMovies(): List<MoviesTable>

    @Query( "SELECT * FROM MoviesTable " +
            "WHERE title LIKE  '%'|| :searching ||'%' " +
            "" )
    fun searchMovies( searching: String ):List<MoviesTable>

    @Query("SELECT EXISTS(SELECT 1 FROM MoviesTable WHERE id = :movieId)")
     fun checkLike(movieId: Int): Boolean
}