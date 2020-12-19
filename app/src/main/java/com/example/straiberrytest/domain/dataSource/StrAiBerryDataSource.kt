package com.example.straiberrytest.domain.dataSource

import androidx.paging.rxjava2.RxPagingSource
import com.example.straiberrytest.BuildConfig
import com.example.straiberrytest.data.PER_PAGE
import com.example.straiberrytest.data.source.remote.ApiService
import com.example.straiberrytest.domain.model.DefaultPhoto
import com.example.straiberrytest.domain.model.DefaultPhotoResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class StrAiBerryDataSource(
    private val apiService: ApiService) : RxPagingSource<Int, DefaultPhoto>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, DefaultPhoto>> {
        val position = params.key ?: 1

        return apiService.loadFlickrDefaultPhotos(position ,PER_PAGE , BuildConfig.FLICKR_API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { toLoadResult(it, position) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(
        data: DefaultPhotoResponse,
        position: Int
    ): LoadResult<Int, DefaultPhoto> {
        return LoadResult.Page(
            data = data.photos.photo,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position == data.photos.pages) null else position + 1
        )
    }
}