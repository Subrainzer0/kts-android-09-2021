package ru.shanin.madarareddit.networking.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChildTopModel (
    @Json(name = "kind")
    val kind: String?,

    @Json(name = "data")
    val data: ChildData
)