package ru.shanin.madarareddit.networking.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopData (
    @Json(name = "children")
    val children: List<ChildTopModel>,

    @Json(name = "after")
    val after: String?,

    @Json(name = "before")
    val before: String?,
)
