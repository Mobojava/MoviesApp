package com.movis.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.movis.app.R
import com.movis.app.databinding.LayoutGenresBinding
import com.movis.app.model.Model_genres

class Adapter_genres(
    val data: List<Model_genres>,
    val onCategoryClick: OnCategoryClick) :
    RecyclerView.Adapter<Adapter_genres.RecyclerViewHolder>() {
    private var number = 0

    lateinit var binding: LayoutGenresBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        binding = LayoutGenresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        // bind data
        holder.bindData(position)

        // show image for item  (All Categories)
        val imgIcon = holder.itemView.findViewById<ImageView>(R.id.img_item_genres)
        imgIcon.visibility =
            if (data[position].name == "All Categories") View.VISIBLE else View.GONE



        if (number < data.size){
            // Animation for items
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
            number ++
        }
        holder.itemView.setOnClickListener {
            onCategoryClick.OnCategoryClick(data[position].id?.toInt() ?: 1, data[position].name.toString() )
        }


    }

    override fun getItemCount() = data.size

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(position: Int) {
            binding.txtGenres.text = data[position].name
        }
    }

    interface OnCategoryClick {
        fun OnCategoryClick(id: Int,name: String )
    }
}