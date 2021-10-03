package ru.shanin.madarareddit.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import timber.log.Timber

object Networking {

    private val okhttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(
            HttpLoggingInterceptor {
                Timber.tag("Network").d(it)
            }
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .addInterceptor(TokenHeaderInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://oauth.reddit.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okhttpClient)
        .build()

    val githubApi: GithubApi
        get() = retrofit.create()

}
