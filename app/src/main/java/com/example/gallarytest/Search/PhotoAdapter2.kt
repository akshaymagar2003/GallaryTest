package com.example.gallarytest.Search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gallarytest.databinding.ItemPhotoBinding
import com.example.gallarytest.network.Photo
import com.squareup.picasso.Picasso

class PhotoAdapter2 : RecyclerView.Adapter<PhotoAdapter2.PhotoViewHolder>() {
    private var photos: List<PhotoItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPhotoBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = photos[position]
        holder.bind(photo)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    fun updatePhotos(newPhotos: List<PhotoItem>) {
        photos = newPhotos
        notifyDataSetChanged()
    }

    inner class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: PhotoItem) {
            val imageView = binding.imageView2

            Glide.with(binding.root).load(photo.url_s).into(imageView);

        }
    }
}
