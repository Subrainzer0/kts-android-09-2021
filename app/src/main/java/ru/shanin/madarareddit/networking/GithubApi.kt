package ru.shanin.madarareddit.networking

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.shanin.madarareddit.networking.models.TopModel

interface GithubApi {
    @GET("top")
    suspend fun getTop(): TopModel

    @GET("top")
    suspend fun getTopWithIndex(
        @Query("after") after: String
    ): TopModel

    @FormUrlEncoded
    @POST("api/vote")
    suspend fun vote(@Field("id") id: String, @Field("dir") dir: String)
}
