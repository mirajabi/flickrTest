package com.example.straiberrytest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.straiberrytest.BuildConfig
import com.example.straiberrytest.data.source.remote.ApiService
import com.example.straiberrytest.domain.dataSource.StrAiBerryDataSource
import com.example.straiberrytest.domain.model.DefaultPhoto
import com.example.straiberrytest.domain.model.PhotoDetail
import com.example.straiberrytest.domain.repository.StrAIberryRepository
import io.reactivex.Flowable
import io.reactivex.Single

class StrAIberryRepositoryImp(private val retrofitService: ApiService) : StrAIberryRepository {

    override fun loadPhotoDetail(photoId: String?, apiKey: String): Single<PhotoDetail> {
        return retrofitService.loadPhotoDetail(photoId , BuildConfig.FLICKR_API_KEY)
    }

    override fun loadFlickrDefaultPhotos(
        apiKey: String
    ): Flowable<PagingData<DefaultPhoto>> {
        return Pager(
            PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 20
            )
        ) {
            StrAiBerryDataSource(retrofitService)
        }.flowable
    }

}