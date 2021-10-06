package ru.shanin.madarareddit.networking.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChildData(

    @Json(name = "title")
    val title: String,

    @Json(name = "subreddit_name_prefixed")
    val subredditNamePrefixed: String,

    @Json(name = "id")
    val id: String,

    @Json(name = "author")
    val author: String,

    @Json(name = "url")
    val link: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "score")
    val score: Int,

    @Json(name = "likes")
    val likes: Boolean?
)
