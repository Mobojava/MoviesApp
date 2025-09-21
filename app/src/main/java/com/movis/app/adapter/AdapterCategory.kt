package com.movis.app.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movis.app.ApiManager.ApiManager
import com.movis.app.databinding.ItemCategoryBinding
import com.movis.app.model.Model_genres
import com.movis.app.model.MovieItem

class AdapterCategory(
    val data: List<Model_genres>,
    val apiManager: ApiManager,
    val onCategoryClickListener: OnCategoryClickListener
) : RecyclerView.Adapter<AdapterCategory.AdapterCategoryViewHolder>(),
    AdapterCategoryMovies.OnMovieClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdapterCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterCategoryViewHolder, position: Int) {
        holder.bindData(data[position])

        holder.itemView.alpha = 0f
        holder.itemView.translationY = -300f
        holder.itemView.translationX = -300f

        holder.itemView.animate()
            .alpha(1f)
            .translationY(0f)
            .translationX(0f)
            .setDuration(300)
            .setStartDelay((position * 50).toLong())
            .start()
    }

    override fun getItemCount() = data.size

    override fun onMovieClick(movie: MovieItem) {
        onCategoryClickListener.onCategoryClick(movie)
    }

    inner class AdapterCategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(genre: Model_genres) {
            binding.nameCategory.text = genre.name

            apiManager.getCategory(
                genre.id?.toInt()!!,
                object : ApiManager.ApiCallback<List<MovieItem>> {
                    override fun onSuccess(data: List<MovieItem>) {
                        val layoutManager = LinearLayoutManager(
                            binding.root.context,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        layoutManager.reverseLayout = true
                        layoutManager.stackFromEnd = true

                        binding.RecyclerCategoryItem.apply {
                            this.layoutManager = layoutManager
                            adapter = AdapterCategoryMovies(data, this@AdapterCategory)
                        }


                    }

                    override fun onError(data: String) {
                        Log.e("Retrofit", "Error loading movies for genre ${genre.name}: $data")
                    }
                })

            binding.viewAll.setOnClickListener {
                onCategoryClickListener.onCategoryViewAll(
                    data[position].id!!,
                    data[position].name!!
                )
            }
        }
    }


    interface OnCategoryClickListener {
        fun onCategoryClick(movie: MovieItem)
        fun onCategoryViewAll(movieID: Int, movieCategory: String)
    }

}
