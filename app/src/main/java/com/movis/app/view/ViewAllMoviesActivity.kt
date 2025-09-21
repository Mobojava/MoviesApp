package com.movis.app.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movis.app.ApiManager.ApiManager
import com.movis.app.adapter.Adapter_ViewAllMovies
import com.movis.app.databinding.ActivityViewAllMoviesBinding
import com.movis.app.model.CategoryMoviesResponse

class ViewAllMoviesActivity : AppCompatActivity(), Adapter_ViewAllMovies.OnCategoryClick {
    lateinit var binding: ActivityViewAllMoviesBinding

    var pageMovies = 1
    var movie_id = 1
    val apiManager = ApiManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movie_id = intent.getIntExtra("movie_id", -1000)
        val movie_name = intent.getStringExtra("movie_name").toString()
        binding.moviesTitle.text = movie_name

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val views = listOf(
                    binding.appbarMain,
                    binding.RecyclerViewAll
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

        GetMoviesInfoServer()
        endRecyclerViewItwm()

        pageListnear()
    }

    private fun GetMoviesInfoServer() {
        apiManager.getMoviesList(
            movie_id,
            pageMovies,
            object : ApiManager.ApiCallback<CategoryMoviesResponse> {
                override fun onSuccess(data: CategoryMoviesResponse) {

                    initRecyclerview(data)
                    binding.animationLotti.visibility = View.GONE

                }

                override fun onError(data: String) {
                    Log.v("Retrofit", data.toString())
                    binding.animationLotti.visibility = View.GONE
                }


            })

    }

    private fun pageListnear() {
        binding.imgNext.setOnClickListener {
            pageMovies ++
            GetMoviesInfoServer()
            binding.textView.text = pageMovies.toString()
            binding.imgBack.animate()
                .alpha(1f)
                .setDuration(500)
                .start()
        }
        binding.imgBack.setOnClickListener {
            if (pageMovies > 1){
                pageMovies --
                GetMoviesInfoServer()
                binding.textView.text = pageMovies.toString()

            }else if (pageMovies == 1){
                binding.imgBack.animate()
                    .alpha(0f)
                    .setDuration(500)
                    .start()
            }
        }
        if (pageMovies == 1){
            binding.imgBack.animate()
                .alpha(0f)
                .setDuration(500)
                .start()
        }
    }

    private fun endRecyclerViewItwm() {
        binding.RecyclerViewAll.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItem == totalItemCount - 1) {
                    binding.ConstraintLayoutPage.animate()
                        .translationY(0f)
                        .setDuration(1000)
                        .start()
                }
            }
        })

    }

    private fun initRecyclerview(data: CategoryMoviesResponse) {


        binding.RecyclerViewAll.adapter = Adapter_ViewAllMovies(data, this)
        binding.RecyclerViewAll.layoutManager =
            GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)


    }

    override fun OnCategoryClick(data: Int) {
       val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("movie_id", data)
        startActivity( intent )
        overridePendingTransition( android.R.anim.slide_in_left,android.R.anim.slide_out_right)

    }
}