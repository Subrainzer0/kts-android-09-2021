package ru.shanin.madarareddit.networking

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.shanin.madarareddit.networking.models.TopModel

interface GithubApi {

    @GET("top")
    suspend fun getTopWithIndex(
        @Query("before") before: String?,
        @Query("after") after: String?,
        @Query("count") count: Int?,
        @Query("limit") limit: Int = 10
    ): TopModel

    @FormUrlEncoded
    @POST("api/vote")
    suspend fun vote(@Field("id") id: String, @Field("dir") dir: String)
}
