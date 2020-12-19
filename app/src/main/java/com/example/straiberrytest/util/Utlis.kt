package com.example.straiberrytest.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.placeholderOf
import com.example.straiberrytest.MainActivity
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
fun Fragment.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
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

fun calculateNoOfColumns(context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density
    val scalingFactor = 200
    val columnCount = (dpWidth / scalingFactor).toInt()
    return if (columnCount >= 2) columnCount else 2
}

fun FragmentActivity.switchTo(
    activity: Activity,
    activityTwo: AppCompatActivity,
    transaction: Any? = null,
    key: String? = "data"
) {
    val intent = Intent(activity, activityTwo::class.java)
    intent.putExtra(key, transaction?.toString())
    startActivity(intent)
    activity.overridePendingTransition(0, 0)
}

fun switch(view: View, fragment: Fragment, transaction: Any? = null) {
    val args = Bundle()
    args.putString("transaction", transaction.toString())
    fragment.arguments = args
    (view.context as MainActivity).supportFragmentManager.beginTransaction()
        .replace(R.id.gallery_container, fragment).addToBackStack("tag")
        .commit()
}

