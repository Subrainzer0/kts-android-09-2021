package ru.shanin.madarareddit.ui.main.mapper

import ru.shanin.madarareddit.networking.models.ChildData
import ru.shanin.madarareddit.ui.main.mapper.UiModelsContainer.UiTopModel
import ru.shanin.madarareddit.ui.main.mapper.UiModelsContainer.UiTopWithoutImageModel

object TopToUiMapper {
    private val linkList = mutableListOf<UiModelsContainer>()

    fun topListToUiModel(list: List<ChildData>): List<UiModelsContainer> {
        list.forEach { child ->
            val path = child.link
            if (path.contains(".jpg")) {
                linkList.add(child.childToUi())
            }
            else {
                linkList.add(child.childToUiWithoutImage())
            }
        }
        return linkList.toList()
    }

    private fun ChildData.childToUi(): UiTopModel {
        return UiTopModel(title, subredditNamePrefixed, id, author, link, name, score, likes)
    }

    private fun ChildData.childToUiWithoutImage(): UiTopWithoutImageModel {
        return UiTopWithoutImageModel(title, subredditNamePrefixed, id, author, name, score, likes)
    }
}
