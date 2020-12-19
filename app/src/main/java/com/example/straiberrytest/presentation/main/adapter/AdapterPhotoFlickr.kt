package com.example.straiberrytest.presentation.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.straiberrytest.R
import com.example.straiberrytest.domain.model.DefaultPhoto
import com.example.straiberrytest.util.loadImageFull


class AdapterPhotoFlickr :
    PagingDataAdapter<DefaultPhoto, AdapterPhotoFlickr.ShowFlickrPhoto>(
        BookComparator
    ) {

    private var listBooks: ArrayList<ShowFlickrPhoto> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowFlickrPhoto {
        return ShowFlickrPhoto(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_photos, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ShowFlickrPhoto, position: Int) {
        holder.bindItems(getItem(position))
    }

    inner class ShowFlickrPhoto(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var image: ImageView = itemView.findViewById(R.id.iv_thumbnail)
        private var title: TextView = itemView.findViewById(R.id.tv_title)
        private var backgroundItem: ConstraintLayout = itemView.findViewById(R.id.background_item)

        @SuppressLint("SetTexxtI18n", "SetTextI18n")
        fun bindItems(photos: DefaultPhoto?) {
            itemView.run {
                backgroundItem.setOnClickListener(this@ShowFlickrPhoto)

                title.text = photos?.title
                image.loadImageFull(photos?.imageUrl)
            }

        }

        override fun onClick(view: View?) {
            when (view?.id) {
                R.id.background_item -> {
                    val model = getItem(layoutPosition)
                    val id = model?.photoId
                    println("PRINTIDPHOTO : $id")
                }
            }
        }
    }

    companion object {
        const val PHOTO_KEY = "data"

        private val BookComparator = object : DiffUtil.ItemCallback<DefaultPhoto>() {
            override fun areItemsTheSame(
                oldItem: DefaultPhoto,
                newItem: DefaultPhoto
            ): Boolean {
                return oldItem.photoId == newItem.photoId
            }

            override fun areContentsTheSame(
                oldItem: DefaultPhoto,
                newItem: DefaultPhoto
            ): Boolean =
                oldItem == newItem
        }
    }
}