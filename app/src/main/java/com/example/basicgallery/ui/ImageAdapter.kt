package com.example.basicgallery.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.basicgallery.databinding.ItemImageRowBinding
import com.example.basicgallery.model.Image

class ImageAdapter(
    private var imageList: ArrayList<Image>
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(private val binding: ItemImageRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var image: ImageView? = null

        init {
            image = binding.rowImage
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        return ImageViewHolder(
            ItemImageRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentImage = imageList[position]
        Glide.with(holder.itemView.context)
            .load(currentImage.imagePath)
            .apply(RequestOptions().centerCrop())
            .into(holder.image!!)

        holder.image?.setOnClickListener {
            val intent = Intent(holder.itemView.context, ImageDetailActivity::class.java)
            intent.putExtra("path", currentImage.imagePath)
            intent.putExtra("name", currentImage.imageName)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}