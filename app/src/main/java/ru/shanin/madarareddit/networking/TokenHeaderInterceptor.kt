package ru.shanin.madarareddit.networking

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import ru.shanin.madarareddit.data.AuthRepository.Auth

object TokenHeaderInterceptor : Interceptor {
    private val token = Auth.token

    override fun intercept(chain: Chain): Response {
        return if (token != null) {
            val requestWithTokenHeader = chain.request()
                .newBuilder()
                .addHeader("Authorization", " Bearer $token")
                .build()

            chain.proceed(requestWithTokenHeader)
        }
        else {
            chain.proceed(chain.request())
        }
    }
}
