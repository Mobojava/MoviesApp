package com.movis.app.room

import android.R
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MoviesTable(
    @PrimaryKey
    val id: Int?,
    val title: String?,
    val poster: String?,
    val country: String?,
    val runtime: String?,
    val year: String?,
    val imdbRating: String?,
    val imdbVotes: String?,
    val plot: String?,
    val genres: String?,
    val released: String?
)


