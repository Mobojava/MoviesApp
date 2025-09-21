package com.movis.app.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movis.app.databinding.ItemWatchlistBinding
import com.movis.app.room.MoviesTable

class Adapter_watchlist(
   // var dataMovies: List<MoviesTable>,
    var dataMovies: MutableList<MoviesTable> = mutableListOf(),
    val eventclick: Adapter_watchlist.eventClick
) :
    RecyclerView.Adapter<Adapter_watchlist.Adapter_watchlistViewHolder>() {
    lateinit var binding: ItemWatchlistBinding
    var isCHeck = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter_watchlistViewHolder {
        binding = ItemWatchlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Adapter_watchlistViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: Adapter_watchlistViewHolder, position: Int) {
        holder.bindData(position)

        holder.itemView.setOnClickListener {
            eventclick.watchListEvent(dataMovies[position].id!!)
        }


        if (isCHeck) {
            holder.itemView.translationY = 300f
            holder.itemView.translationX = -300f
            holder.itemView.animate()
                .translationY(0f)
                .translationX(0f)
                .setDuration(400)
                .setStartDelay(100 * position.toLong())
                .start()
            isCHeck = false
        } else {
            holder.itemView.translationY = 300f
            holder.itemView.translationX = 300f
            holder.itemView.animate()
                .translationY(0f)
                .translationX(0f)
                .setDuration(400)
                .setStartDelay(100 * position.toLong())
                .start()

             isCHeck = true

        }
    }

    override fun getItemCount() = dataMovies.size


    inner class Adapter_watchlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(position: Int) {
            Glide.with(binding.root.context)
                .load(dataMovies[position].poster)
                .into(binding.imageWatchlist)
            Log.v("url", dataMovies[position].poster.toString())
            binding.txtTitleWatchList.text = dataMovies[position].title
            binding.yearWatchlist.text = dataMovies[position].year
            binding.ratingWatchlist.text = dataMovies[position].imdbRating
            binding.timeWatchlist.text = dataMovies[position].runtime


        }
    }

    interface eventClick {
        fun watchListEvent(data: Int)
    }
}