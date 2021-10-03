package ru.shanin.madarareddit.networking.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopModel (
    @Json(name = "data")
    val data: TopData,
)
