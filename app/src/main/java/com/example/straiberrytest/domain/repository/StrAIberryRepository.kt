package com.example.straiberrytest.domain.repository

import androidx.paging.PagingData
import com.example.straiberrytest.BuildConfig
import com.example.straiberrytest.domain.model.DefaultPhoto
import com.example.straiberrytest.domain.model.PhotoDetail
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * To make an interaction between [StrAIberryRepositoryImp] & [GetStrAIberryUseCase]
 * */
interface StrAIberryRepository {

    fun loadPhotoDetail(photoId: String?, apiKey: String = BuildConfig.FLICKR_API_KEY): Single<PhotoDetail>

    fun loadFlickrDefaultPhotos(
        apiKey: String = BuildConfig.FLICKR_API_KEY
    ): Flowable<PagingData<DefaultPhoto>>
}