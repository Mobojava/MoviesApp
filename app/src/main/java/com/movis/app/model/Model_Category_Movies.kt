package com.movis.app.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CategoryMoviesResponse(
    @SerializedName("data")
    val data: List<MovieItem>,
    @SerializedName("metadata")
    val metadata: Metadata
)

@Parcelize
@Keep
data class MovieItem(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("poster")
    val poster: String?,
    @SerializedName("year")
    val year: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("imdb_rating")
    val imdbRating: String?,
    @SerializedName("genres")
    val genres: List<String?>?,
    @SerializedName("images")
    val images: List<String?>?
): Parcelable

data class Metadata(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("page_count")
    val pageCount: Int,
    @SerializedName("total_count")
    val totalCount: Int
)
