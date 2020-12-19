package com.example.straiberrytest.domain.model

import okhttp3.ResponseBody


data class ErrorModel(
    val message: String?,
    val code: Int?,
    var errorStatus: ErrorStatus,
    var responseBody: ResponseBody? = null
) {

    constructor(message: String?, errorStatus: ErrorStatus) : this(
        message,
        null,
        errorStatus,
        null
    )

    fun getErrorMessage(): String {
        return when (errorStatus) {
            ErrorStatus.NO_CONNECTION -> "دستگاه شما به اینترنت متصل نمی باشد"
            ErrorStatus.BAD_RESPONSE -> "مشکلی به وجود آمده بعدا وارد شوید"
            ErrorStatus.TIMEOUT -> "پاسخی دریافت نشد از سرور بعدا تلاش کنید"
            ErrorStatus.EMPTY_RESPONSE -> "مقادیری یافت نشد"
            ErrorStatus.NOT_DEFINED -> "اتصال برقرار نیست"
            ErrorStatus.UNAUTHORIZED -> "کاربر شناسایی نشد"
            ErrorStatus.UNPROCESSABLE -> "تلاش دوباره اتصال برقرار نشد"
            ErrorStatus.FOUND -> "موردی یافت نشد"
            ErrorStatus.FORBIDDEN -> "دسترسی ممنوع می باشد برای شما"
        }
    }

    enum class ErrorStatus {
        NO_CONNECTION,
        BAD_RESPONSE,
        TIMEOUT,
        EMPTY_RESPONSE,
        NOT_DEFINED,
        UNAUTHORIZED,
        UNPROCESSABLE,
        FOUND,
        FORBIDDEN
    }
}