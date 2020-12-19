package com.example.straiberrytest.di.module

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.straiberrytest.BuildConfig.BASE_URL
import com.example.straiberrytest.data.repository.StrAIberryRepositoryImp
import com.example.straiberrytest.data.source.remote.ApiErrorHandle
import com.example.straiberrytest.data.source.remote.ApiService
import com.example.straiberrytest.domain.model.PassData
import com.example.straiberrytest.domain.repository.StrAIberryRepository
import com.example.straiberrytest.util.StringConverterFactory
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.orhanobut.hawk.Hawk


@Module(includes = [ApplicationModule::class])
class NetworkModule {

    companion object {
        @JvmStatic
        var token: String? = null
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        stringConverterFactory: StringConverterFactory,
        scalarsConverterFactory: ScalarsConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .addConverterFactory(stringConverterFactory)
            .addConverterFactory(scalarsConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(context: Context, isNetworkAvailable: Boolean): OkHttpClient {
        Hawk.init(context).build()
        val data = Hawk.get<PassData>("userInfo")

//        val cacheSize = (5 * 1024 * 1024).toLong()
//        val mCache = Cache(context.cacheDir, cacheSize)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .cache(null) // mCache can be used here
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .addInterceptor { chain ->
                var request = chain.request()
                val builder = request.newBuilder()

                if (token != null) {
                    builder.addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer $token")
                } else {
                    if (data?.token != null) {
                        builder.addHeader("Accept", "application/json")
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Authorization", "Bearer ${data.token}")
                    } else {
                        builder.addHeader("Accept", "application/json")
                            .addHeader("Content-Type", "application/json")
                    }
                }
                request = builder.build()

//                request = if (isNetworkAvailable) request.newBuilder().header(
//                    "Cache-Control",
//                    "public, max-age=" + 5
//                ).build()
//                else request.newBuilder().header(
//                    "Cache-Control",
//                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
//                ).build()
                chain.proceed(request)
            }
        return client.build()
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun providesStringConverterFactory(): StringConverterFactory {
        return StringConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesScalarsConverterFactory(): ScalarsConverterFactory {
        return ScalarsConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun providesApiErrorHandle(): ApiErrorHandle {
        return ApiErrorHandle()
    }

    @Provides
    @Singleton
    fun provideIsNetworkAvailable(context: Context): Boolean {
        return checkNetworkState(context)
    }

    @Suppress("DEPRECATION")
    private fun checkNetworkState(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideStrAiBerryRepository(
        retrofitService: ApiService
    ): StrAIberryRepository {
        return StrAIberryRepositoryImp(retrofitService)
    }
}