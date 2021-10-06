package ru.shanin.madarareddit.ui.main

data class ItemWithoutImage(
    val subreddit: String,
    val author: String,
    val content: String,
    var likeCounter: Int,
    val uuid: String
)
