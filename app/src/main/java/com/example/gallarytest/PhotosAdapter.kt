package com.example.gallarytest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gallarytest.Search.PhotoItem
import com.example.gallarytest.databinding.PhotoItemBinding
import com.example.gallarytest.network.Photo
import com.squareup.picasso.Picasso

class PhotosAdapter : PagingDataAdapter<Photo, PhotosAdapter.PhotoViewHolder>(PhotoComparator) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PhotoItemBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = getItem(position)
        photo?.let {
            holder.bind(it)
        }
    }




    inner class PhotoViewHolder(private val binding: PhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {
            val imageView = binding.imageView

            Glide.with(binding.root).load(photo.url_s).into(imageView);
        }


    }

    object PhotoComparator : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }
}
