package ru.shanin.madarareddit.ui.main.mapper

sealed class UiModelsContainer {

    data class UiTopModel(
        val title: String,
        val subredditNamePrefixed: String,
        val id: String,
        val author: String,
        val link: String,
        val name: String,
        var score: Int,
        var isLiked: Boolean?
    ) : UiModelsContainer()

    data class UiTopWithoutImageModel(
        val title: String,
        val subredditNamePrefixed: String,
        val id: String,
        val author: String,
        val name: String,
        var score: Int,
        var isLiked: Boolean?
    ) : UiModelsContainer()
}
