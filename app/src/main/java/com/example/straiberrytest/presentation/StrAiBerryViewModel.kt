package com.example.straiberrytest.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.example.straiberrytest.data.source.remote.ApiErrorHandle
import com.example.straiberrytest.domain.model.DefaultPhoto
import com.example.straiberrytest.domain.model.ErrorModel
import com.example.straiberrytest.domain.model.PhotoDetail
import com.example.straiberrytest.domain.repository.StrAIberryRepository
import com.example.straiberrytest.domain.usecase.GetStraAiBerryUseCase
import io.reactivex.Flowable
import javax.inject.Inject


class StrAiBerryViewModel @Inject constructor(
    private val getStraAiBerryUseCase: GetStraAiBerryUseCase,
    private val apiErrorHandle: ApiErrorHandle,
    private val repository: StrAIberryRepository
) : ViewModel() {

    private val TAG = StrAiBerryViewModel::class.java.simpleName

    val getPhotoDetailsReceivedLiveData = MutableLiveData<PhotoDetail>()

    val isLoad = MutableLiveData<Boolean>()
    val errorModel = MutableLiveData<ErrorModel>()

    init {
        isLoad.value = false
    }

    fun loadFlickrDefaultPhotos(): Flowable<PagingData<DefaultPhoto>> {
        return repository.loadFlickrDefaultPhotos().cachedIn(viewModelScope)
    }

    fun loadPhotoDetails(photoId: String?) {
        getStraAiBerryUseCase.saveIdPhoto(photoId)
        getStraAiBerryUseCase.execute(
            onSuccess = {
                isLoad.value = true
                getPhotoDetailsReceivedLiveData.value = it
            },
            onError = {
                it.printStackTrace()
                errorModel.value = apiErrorHandle.traceErrorException(it)
            }
        )
    }

}