package com.movis.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movis.app.databinding.ItemWatchlistBinding
import com.movis.app.model.MovieItem

class Adapter_search(
    val data: List<MovieItem>,
    val clickevent: Adapter_search.clickEventSearch
) :
    RecyclerView.Adapter<Adapter_search.Adapter_search_ViewHolder>() {
    lateinit var binding: ItemWatchlistBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Adapter_search_ViewHolder {
        binding = ItemWatchlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Adapter_search_ViewHolder(binding.root)
    }

    override fun onBindViewHolder(
        holder: Adapter_search_ViewHolder,
        position: Int
    ) {
        holder.bindData(position)

        holder.itemView.translationY = 300f
        holder.itemView.translationX = -300f
        holder.itemView.animate()
            .translationY(0f)
            .translationX(0f)
            .setDuration(500)
            .setStartDelay(100 * position.toLong())
            .start()

        holder.itemView.setOnClickListener {
            clickevent.searchEvent( data[position].id!! )
        }
    }

    override fun getItemCount() = data.size

    inner class Adapter_search_ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(position: Int) {
            Glide.with(binding.root)
                .load(data[position].poster)
                .into(binding.imageWatchlist)
            binding.txtTitleWatchList.text = data[position].title
            binding.yearWatchlist.text = data[position].year
            binding.ratingWatchlist.text = data[position].imdbRating
            binding.timeWatchlist.text = "1 hours"
        }

    }

    interface clickEventSearch {
        fun searchEvent(id: Int)
    }
}