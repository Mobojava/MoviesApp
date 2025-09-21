package com.movis.app.ApiManager

import com.movis.app.ApiService.ApiService
import com.movis.app.model.CategoryMoviesResponse
import com.movis.app.model.Model_Movies_Info
import com.movis.app.model.Model_genres
import com.movis.app.model.Model_user
import com.movis.app.model.MovieItem
import com.movis.app.model.RegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://moviesapi.ir/"

class ApiManager {

    val apiService: ApiService

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)
    }


    fun getGenres(apiCallback: ApiCallback<List<Model_genres>>) {

        apiService.getGenres().enqueue(object : Callback<List<Model_genres>> {
            override fun onResponse(
                call: Call<List<Model_genres>?>?,
                response: Response<List<Model_genres>?>?
            ) {
                apiCallback.onSuccess(response?.body()!!)
            }

            override fun onFailure(
                call: Call<List<Model_genres>?>?,
                t: Throwable?
            ) {
                apiCallback.onError(t?.message!!)
            }


        })

    }

    fun getCategory(category: Int, apiCallback: ApiCallback<List<MovieItem>>) {
        apiService.getCategory(category).enqueue(object : Callback<CategoryMoviesResponse> {
            override fun onResponse(
                call: Call<CategoryMoviesResponse>,
                response: Response<CategoryMoviesResponse>
            ) {
                if (response.isSuccessful) {
                    apiCallback.onSuccess(response.body()?.data ?: emptyList())
                } else {
                    apiCallback.onError("Response error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CategoryMoviesResponse>, t: Throwable) {
                apiCallback.onError(t.message ?: "Unknown error")
            }
        })
    }

    fun getMoviesInfo(MoviesID: Int, apiCallback: ApiCallback<Model_Movies_Info>) {

        apiService.getMoviesInfo(MoviesID).enqueue(object : Callback<Model_Movies_Info> {
            override fun onResponse(
                call: Call<Model_Movies_Info?>?,
                response: Response<Model_Movies_Info?>?
            ) {
                apiCallback.onSuccess(response?.body()!!)
            }

            override fun onFailure(
                call: Call<Model_Movies_Info?>?,
                t: Throwable?
            ) {
                apiCallback.onError(t?.message!!)
            }

        })
    }

    fun getMoviesList(
        MoviesID: Int,
        MoviesPage: Int,
        apiCallback: ApiCallback<CategoryMoviesResponse>
    ) {

        apiService.getMoviesList(MoviesID, MoviesPage)
            .enqueue(object : Callback<CategoryMoviesResponse> {
                override fun onResponse(
                    call: Call<CategoryMoviesResponse?>?,
                    response: Response<CategoryMoviesResponse?>?
                ) {
                    apiCallback.onSuccess(response?.body()!!)
                }

                override fun onFailure(
                    call: Call<CategoryMoviesResponse?>?,
                    t: Throwable?
                ) {
                    apiCallback.onError(t?.message!!)
                }


            })

    }

    fun searchMovies(moviesName: String, apiCallback: ApiCallback<CategoryMoviesResponse>) {

        apiService.searchMovies(moviesName).enqueue(object : Callback<CategoryMoviesResponse> {
            override fun onResponse(
                call: Call<CategoryMoviesResponse?>?,
                response: Response<CategoryMoviesResponse?>?
            ) {
                apiCallback.onSuccess(response?.body()!!)
            }

            override fun onFailure(
                call: Call<CategoryMoviesResponse?>?,
                t: Throwable?
            ) {
                apiCallback.onError(t?.message!!)
            }

        })
    }

    fun registerUser(data : RegisterRequest, apiCallback: ApiCallback<Model_user>){
        apiService.registerUser(data).enqueue(object : Callback<Model_user>{
            override fun onResponse(
                call: Call<Model_user?>?,
                response: Response<Model_user?>?
            ) {
                if (response?.isSuccessful == true && response.body() != null) {
                    apiCallback.onSuccess(response.body()!!)
                } else {
                    apiCallback.onError("Error: ${response?.code()}")
                }
            }

            override fun onFailure(
                call: Call<Model_user?>?,
                t: Throwable?
            ) {
                apiCallback.onError( t?.message.toString() )
            }

        })
    }

    interface ApiCallback<T> {
        fun onSuccess(data: T)
        fun onError(data: String)
    }
}