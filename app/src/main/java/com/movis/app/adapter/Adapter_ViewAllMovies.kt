package com.movis.app.adapter

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.movis.app.R
import com.movis.app.databinding.ItemViewAllBinding
import com.movis.app.model.CategoryMoviesResponse

class Adapter_ViewAllMovies(
    val responseData: CategoryMoviesResponse,
    val onCategoryClick: OnCategoryClick
) :
    RecyclerView.Adapter<Adapter_ViewAllMovies.Adapter_ViewAllMovies_ViewHolder>() {
    lateinit var binding: ItemViewAllBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Adapter_ViewAllMovies_ViewHolder {
        binding = ItemViewAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Adapter_ViewAllMovies_ViewHolder(binding.root)
    }

    override fun onBindViewHolder(
        holder: Adapter_ViewAllMovies_ViewHolder,
        position: Int
    ) {
        holder.bindData(position)

        holder.itemView.translationX = -300f
        // holder.itemView.translationY = -300f

        holder.itemView.animate()
            .translationX(0f)
            //  .translationY(0f)
            .setDuration((position * 200).toLong())
            .start()

        holder.itemView.setOnClickListener {
            onCategoryClick.OnCategoryClick( responseData.data[position].id!! )
        }
    }

    override fun getItemCount() = responseData.data.size

    inner class Adapter_ViewAllMovies_ViewHolder(itemview: View) :
        RecyclerView.ViewHolder(itemview) {
        fun bindData(position: Int) {

            Glide.with(binding.root.context)
                .load(responseData.data[position].poster)
                .centerCrop()
                .override(300, 400)
                .transform(RoundedCorners(20))
                .into(binding.imageViewAll)


            binding.txtViewAll.text = responseData.data[position].title.toString()

        }
    }

    interface OnCategoryClick {
        fun OnCategoryClick(data: Int)
    }
}