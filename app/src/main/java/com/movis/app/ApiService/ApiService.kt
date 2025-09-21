package com.movis.app.ApiService

import com.movis.app.model.CategoryMoviesResponse
import com.movis.app.model.Model_Movies_Info
import com.movis.app.model.Model_genres
import com.movis.app.model.Model_user
import com.movis.app.model.MovieItem
import com.movis.app.model.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/v1/genres")
    fun getGenres(): Call<List<Model_genres>>


    @GET("api/v1/genres/{genre_id}/movies?page=1")
    fun getCategory(
        @Path("genre_id") genreId: Int
    ): Call<CategoryMoviesResponse>


    @GET("api/v1/movies/{MoviesID}")
    fun getMoviesInfo(
        @Path("MoviesID") moviesId: Int
    ): Call<Model_Movies_Info>


    @GET("api/v1/genres/{MoviesID}/movies")
    fun getMoviesList(
        @Path("MoviesID") moviesID: Int,
        @Query("page") moviesPage: Int
    ): Call<CategoryMoviesResponse>


    @GET("api/v1/movies")
    fun searchMovies(
        @Query("q") name: String
    ): Call<CategoryMoviesResponse>


    @POST("api/v1/register")
    fun registerUser(
        @Body request: RegisterRequest
    ): Call<Model_user>


}