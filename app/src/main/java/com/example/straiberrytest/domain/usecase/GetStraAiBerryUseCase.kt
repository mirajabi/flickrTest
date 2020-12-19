package com.example.straiberrytest.domain.usecase

import com.example.straiberrytest.BuildConfig.FLICKR_API_KEY
import com.example.straiberrytest.domain.model.PhotoDetail
import com.example.straiberrytest.domain.repository.StrAIberryRepository
import com.example.straiberrytest.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import okhttp3.ResponseBody
import javax.inject.Inject


class GetStraAiBerryUseCase @Inject constructor(
    private val repository: StrAIberryRepository
) :
    SingleUseCase<PhotoDetail>() {

    private var photoId: String? = null
    fun saveIdPhoto(photoId: String?) {
        this.photoId = photoId
    }

    override fun buildUseCaseSingle(): Single<PhotoDetail> {
        return repository.loadPhotoDetail(photoId ,FLICKR_API_KEY)
    }
}