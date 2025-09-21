package com.movis.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.movis.app.databinding.ItemCategoryItemBinding
import com.movis.app.model.MovieItem

class AdapterCategoryMovies(
    val data: List<MovieItem>,
    val onMovieClickListener: OnMovieClickListener
) :
    RecyclerView.Adapter<AdapterCategoryMovies.AdapterCategoryMoviesViewHolder>() {
    lateinit var binding: ItemCategoryItemBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterCategoryMoviesViewHolder {
        binding =
            ItemCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdapterCategoryMoviesViewHolder(binding.root)
    }

    override fun onBindViewHolder(
        holder: AdapterCategoryMoviesViewHolder,
        position: Int
    ) {
        holder.bindData(position)
        holder.itemView.alpha = 0f
        holder.itemView.translationY = -300f
        holder.itemView.translationX = -300f

        holder.itemView.animate()
            .alpha(1f)
            .translationY(0f)
            .translationX(0f)
            .setDuration(400)
            .setStartDelay((position * 50).toLong())
            .start()
    }

    override fun getItemCount() = data.size

    inner class AdapterCategoryMoviesViewHolder(itemview: View) :
        RecyclerView.ViewHolder(itemview) {
        fun bindData(position: Int) {

            binding.txtItemMovies.text = data[position].title
            Glide.with(binding.root.context)
                .load(data[position].poster)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                .into(binding.imgItemMovies)

            itemView.setOnClickListener {
                onMovieClickListener.onMovieClick(data[position])
            }
        }
    }

    interface OnMovieClickListener {
        fun onMovieClick(movie: MovieItem)
    }

}