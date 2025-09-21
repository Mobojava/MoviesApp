package com.movis.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.movis.app.R
import com.movis.app.databinding.LayoutGenres1Binding
import com.movis.app.databinding.LayoutGenresBinding
import com.movis.app.model.Model_genres



class Adapter_genres1(private val data: List<Model_genres>) :
    RecyclerView.Adapter<Adapter_genres1.RecyclerViewHolder>() {

    private var number = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = LayoutGenres1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bindData(data[position])

        // انیمیشن
        if (number < data.size) {
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
            number++
        }
    }

    override fun getItemCount() = data.size

    inner class RecyclerViewHolder(private val binding: LayoutGenres1Binding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: Model_genres) {
            binding.txtGenres.text = item.name
        }
    }
}
