package com.movis.app.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movis.app.ApiManager.ApiManager
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
                                intent.putExtra("movie_id",movie.id)
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


}