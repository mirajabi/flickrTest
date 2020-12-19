package com.example.straiberrytest

import android.os.Bundle
import com.example.straiberrytest.presentation.PhotosFragment
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateToGalleryPage()
    }

    private fun navigateToGalleryPage() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.gallery_container,
                PhotosFragment.newInstance(),
                "Photo_fragment"
            ).commitAllowingStateLoss()
    }

}