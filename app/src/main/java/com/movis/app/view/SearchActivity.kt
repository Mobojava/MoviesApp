package com.movis.app.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movis.app.ApiManager.ApiManager
import com.movis.app.adapter.Adapter_search
import com.movis.app.adapter.Adapter_watchlist
import com.movis.app.databinding.ActivitySearchBinding
import com.movis.app.model.CategoryMoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() , Adapter_search.clickEventSearch{

    private lateinit var binding: ActivitySearchBinding
    private var searchHandler: Handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null
    val apiManager = ApiManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.edittextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                searchRunnable?.let { searchHandler.removeCallbacks(it) }

                searchRunnable = Runnable {
                    val query = s.toString().trim()
                    if (query.isNotEmpty()) {
                        requestServer(query)
                    }
                }
                searchHandler.postDelayed(searchRunnable!!, 1000)
            }

            override fun afterTextChanged(s: Editable?) {}
        })


    }

    private fun requestServer(query: String) {


        apiManager.apiService.searchMovies(query)
            .enqueue(object : Callback<CategoryMoviesResponse> {
                override fun onResponse(
                    call: Call<CategoryMoviesResponse>,
                    response: Response<CategoryMoviesResponse>
                ) {
                    initRecyclerview(response.body())
                    if ( response.body().data.isEmpty() ){
                        showNotFound()
                    }
                }

                override fun onFailure(
                    call: Call<CategoryMoviesResponse?>?,
                    t: Throwable?
                ) {
                    Log.e("Search", t?.message.toString())
                }


            })

    }

    fun initRecyclerview(responce: CategoryMoviesResponse) {

        binding.linearlayoutHostory.visibility = View.GONE
        binding.linearNotfound.visibility = View.GONE
        binding.RecyclerSearchMovies.visibility = View.VISIBLE

        val myAdapter = Adapter_search(responce.data, this)
        binding.RecyclerSearchMovies.adapter = myAdapter
        binding.RecyclerSearchMovies.layoutManager = LinearLayoutManager(
            this, RecyclerView.VERTICAL, false
        )

    }

     fun showNotFound() {
         binding.linearlayoutHostory.visibility = View.GONE
         binding.linearNotfound.visibility = View.VISIBLE
         binding.RecyclerSearchMovies.visibility = View.GONE
    }

    override fun searchEvent(id: Int) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("movie_id",id)
            startActivity( intent )
    }

}