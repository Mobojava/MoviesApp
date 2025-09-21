package com.movis.app.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movis.app.ApiManager.ApiManager
import com.movis.app.R
import com.movis.app.adapter.AdapterCategory
import com.movis.app.adapter.Adapter_genres
import com.movis.app.databinding.FragmentHomeBinding
import com.movis.app.model.Model_genres
import com.movis.app.model.MovieItem
import com.movis.app.view.MovieDetailActivity
import com.movis.app.view.SearchActivity
import com.movis.app.view.ViewAllMoviesActivity

class FragmentHome : Fragment(), Adapter_genres.OnCategoryClick {

    lateinit var binding: FragmentHomeBinding
    val apiManager = ApiManager()
    var flag = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
//        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
//        (activity as AppCompatActivity).supportActionBar?.title = ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edittextSearch.setOnClickListener {
            startActivity(
                Intent(requireContext(), SearchActivity::class.java)
            )
        }
        binding.itemMenu.setOnClickListener {
            if (flag) {
                binding.NestedScrollViewMenu.animate()
                    .translationX(300f)
                    .setDuration(700)
                    .setStartDelay(100)
                    .start()
                flag = false
                Handler(Looper.getMainLooper()).postDelayed({
                    animItemMeun(true)
                }, 800)
                binding.itemMenu.setImageResource(R.drawable.close)
            } else {
                flag = true
                animItemMeun(false)
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.NestedScrollViewMenu.animate()
                        .translationX(0f)
                        .setDuration(700)
                        .setStartDelay(100)
                        .start()
                }, 1000)
                binding.itemMenu.setImageResource(R.drawable.menu)
            }
        }
        apiManager.getGenres(object : ApiManager.ApiCallback<List<Model_genres>> {

            override fun onSuccess(data: List<Model_genres>) {

                initRecyclerCategory(data)

                val genresList = data.toMutableList()


                val allCategories = Model_genres(
                    id = -1,
                    name = "All Categories"
                )
                genresList.add(0, allCategories)

                val myAdapter = Adapter_genres(genresList, this@FragmentHome)
                binding.RecyclerGenres.adapter = myAdapter
                binding.RecyclerGenres.layoutManager =
                    LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false)
            }

            private fun initRecyclerCategory(data: List<Model_genres>) {
                binding.RecyclerCategory.apply {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    isNestedScrollingEnabled = false
                    adapter = AdapterCategory(
                        data,
                        ApiManager(),
                        object : AdapterCategory.OnCategoryClickListener {
                            override fun onCategoryClick(movie: MovieItem) {

                                val intent =
                                    Intent(requireContext(), MovieDetailActivity::class.java)
                                intent.putExtra("movie_id", movie.id)
                                startActivity(intent)

                            }

                            override fun onCategoryViewAll(movieID: Int, movieCategory: String) {
                                val intent =
                                    Intent(requireContext(), ViewAllMoviesActivity::class.java)
                                intent.putExtra("movie_id", movieID)
                                intent.putExtra("movie_name", movieCategory)
                                startActivity(intent)

                            }

                        })
                }

            }

            override fun onError(data: String) {
                Log.e("retrofit", "genres : \n $data")
            }

        })

        binding.linearlayout4.animate()
            .alpha(1f)
            .translationY(0f)
            .translationX(0f)
            .setDuration(1000)
            .setStartDelay(800)
            .start()

    }

    override fun OnCategoryClick(id: Int, name: String) {
        val intent =
            Intent(requireContext(), ViewAllMoviesActivity::class.java)
        intent.putExtra("movie_id", id)
        intent.putExtra("movie_name", name)
        startActivity(intent)
    }

    fun animItemMeun(item: Boolean) {
        if (item) {
            val button = listOf(
                binding.btn1,
                binding.btn2,
                binding.btn3,
                binding.btn4,
                binding.btn5,
                binding.btn6,
                binding.btn7,
                binding.btn8,
                binding.btn9,
                binding.btn10,
                binding.btn11
            )
            button.forEachIndexed { index, button ->
                button.animate()
                    .translationX(0f)
                    .setDuration(400)
                    .setStartDelay(100 * index.toLong())
                    .start()
            }
        } else {
            val button = listOf(
                binding.btn1,
                binding.btn2,
                binding.btn3,
                binding.btn4,
                binding.btn5,
                binding.btn6,
                binding.btn7,
                binding.btn8,
                binding.btn9,
                binding.btn10,
                binding.btn11
            )
            button.forEachIndexed { index, button ->
                button.animate()
                    .translationX(-200f)
                    .setDuration(400)
                    .setStartDelay(100 * index.toLong())
                    .start()
            }
        }

    }
}