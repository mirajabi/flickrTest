package com.example.straiberrytest.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.placeholderOf
import com.example.straiberrytest.R

fun getImage(imageName: String, context: Context): Int {
    return context.resources.getIdentifier(imageName, "drawable", context.packageName)
}

fun ImageView.loadImageFull(url: String?) {
    val myOptions = RequestOptions()
        .override(this.width, this.height)
        .centerCrop()
    Glide.with(context).load(url).apply(myOptions).apply {
        placeholderOf(R.drawable.placeholder_image)
    }.into(this)
}

fun ImageView.loadLocalImage(name: String) {
    val myOptions = RequestOptions()
        .override(this.width, this.height)
        .centerCrop()
    Glide.with(this).load(getImage(name, this.context)).apply(myOptions).into(this)
}

fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}