package com.example.straiberrytest.data.source.remote

import com.example.straiberrytest.BuildConfig.FLICKR_API_KEY
import com.example.straiberrytest.domain.model.DefaultPhotoResponse
import com.example.straiberrytest.domain.model.PhotoDetail
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("registerName")
    fun registerName(
        @Field("phone") phone: String,
        @Field("name") name: String,
        @Header("Authorization") header: String
    ): Single<ResponseBody>

    @FormUrlEncoded
    @POST("login")
    fun loginPhone(@Field("phone") phone: String): Single<ResponseBody>

    @GET("searchBook")
    fun searchInBook(
        @QueryMap(encoded = true) options: Map<String, String>?
    ): Single<ResponseBody>




    @GET("?method=flickr.photos.getInfo&format=json&nojsoncallback=1")
    fun loadPhotoDetail(
        @Query("photo_id") photoId: String?,
        @Query("api_key") apiKey: String = FLICKR_API_KEY
    ): Single<PhotoDetail>

    @GET("?method=flickr.interestingness.getList&format=json&nojsoncallback=1")
    fun loadFlickrDefaultPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 1,
        @Query("api_key") apiKey: String = FLICKR_API_KEY
    ): Single<DefaultPhotoResponse>

}