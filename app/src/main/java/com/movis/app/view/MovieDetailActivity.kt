package com.movis.app.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movis.app.ApiManager.ApiManager
import com.movis.app.R
import com.movis.app.adapter.Adapter_genres1
import com.movis.app.databinding.ActivityMovieDetailBinding
import com.movis.app.model.Model_Movies_Info
import com.movis.app.model.Model_genres
import com.movis.app.room.MoviesDao
import com.movis.app.room.MoviesTable
import com.movis.app.room.MyDatabase
import com.varunest.sparkbutton.SparkEventListener
import kotlin.String
import kotlin.collections.listOf

class MovieDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityMovieDetailBinding
    val apiManager = ApiManager()
    lateinit var moviesDao: MoviesDao
    lateinit var data_ModelMovies: Model_Movies_Info
    var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieId = intent.getIntExtra("movie_id", -1000)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val views = listOf(
                    binding.toolbar,
                    binding.sparkButton,
                    binding.imageMovieDetail,
                    binding.titleMovieDetail,
                    binding.titleMovieTime,
                    binding.linearlayoutgenres,
                    binding.linearlayout1,
                    binding.txtMovieDescription,
                    binding.linearlayout2,
                    binding.linearlayout3
                )
                views.forEachIndexed { index, view ->
                    view.animate()
                        .translationX(1300f)
                        .setDuration(1000)
                        .setStartDelay((index * 100).toLong())
                        .start()
                }
                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                    overridePendingTransition(
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right
                    )

                }, 1400)
            }

        })


        addToWathList()
        initAnim()
        apiManager.getMoviesInfo(movieId, object : ApiManager.ApiCallback<Model_Movies_Info> {
            override fun onSuccess(data: Model_Movies_Info) {
                Log.v("MovieDetail", data.toString())
                data_ModelMovies = data
                initUI(data)

            }

            override fun onError(data: String) {
                Log.e("MovieDetail", data)
            }

        })

        checkLike()

    }

    private fun checkLike() {
        if(moviesDao.checkLike( movieId ) ){
            binding.sparkButton.isChecked = true
        }else{
            binding.sparkButton.isChecked = false
        }
    }


    private fun addToWathList() {

        moviesDao = MyDatabase.getDatabase(this).moviesDao

        binding.sparkButton.setEventListener(object : SparkEventListener {
            override fun onEvent(button: ImageView?, buttonState: Boolean) {



                if (buttonState) {
                    moviesDao.insertMovies(
                        MoviesTable(
                            data_ModelMovies.id,
                            data_ModelMovies.title,
                            data_ModelMovies.poster,
                            data_ModelMovies.country,
                            data_ModelMovies.runtime,
                            data_ModelMovies.year,
                            data_ModelMovies.imdbRating,
                            data_ModelMovies.imdbVotes,
                            data_ModelMovies.plot,
                            data_ModelMovies.genres?.get(0),
                            data_ModelMovies.released
                        )
                    )
                } else {
                    moviesDao.deletMovies(
                        MoviesTable(
                            data_ModelMovies.id,
                            data_ModelMovies.title,
                            data_ModelMovies.poster,
                            data_ModelMovies.country,
                            data_ModelMovies.runtime,
                            data_ModelMovies.year,
                            data_ModelMovies.imdbRating,
                            data_ModelMovies.imdbVotes,
                            data_ModelMovies.plot,
                            data_ModelMovies.genres?.get(0),
                            data_ModelMovies.released
                        )
                    )
                }
            }

            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {}
            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {}


        })
    }

    private fun initAnim() {

        val views = listOf(
            binding.toolbar,
            binding.sparkButton,
            binding.imageMovieDetail,
            binding.titleMovieDetail,
            binding.titleMovieTime,
            binding.linearlayoutgenres,
            binding.linearlayout1,
            binding.txtMovieDescription,
            binding.linearlayout2,
            binding.linearlayout3
        )

        views.forEachIndexed { index, view ->
            view.animate()
                .translationX(0f)
                .translationY(0f)
                .setDuration(1000)
                .setStartDelay((index * 100).toLong())
                .start()
        }


    }

    @SuppressLint("SetTextI18n")
    private fun initUI(data: Model_Movies_Info) {
        Glide.with(this)
            .load(data.poster)
            .into(binding.imageMovieDetail)

        Glide.with(this)
            .load(R.drawable.topcast)
            .into(binding.topcast)

        binding.titleMovieDetail.text = data.title
        binding.titleMovieTime.text = data.runtime
        binding.txtMovieYears.text = data.year
        binding.txtMovieStar.text = data.imdbRating
        binding.txtMovieReviews.text = data.imdbVotes + "  Reviews"
        binding.txtMovieDescription.text = data.plot
        binding.Country.text = ":  " + data.country
        binding.Genre.text = ":  " + data.genres
        binding.DateRelease.text = ":  " + data.released
        binding.Production.text = ":  " + "AMC Studios"


        // init Catgory
        // List => Model_genres

        val dataList = mutableListOf<Model_genres>()
        var number = 0
        data.genres?.forEach { genre ->
            dataList.add(Model_genres(number, genre))
            number++
        }


        binding.RecyclerGenres1.adapter = Adapter_genres1(dataList)
        binding.RecyclerGenres1.layoutManager =
            LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)

    }


}