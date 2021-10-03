package ru.shanin.madarareddit.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.shanin.madarareddit.ui.main.mapper.UiModelsContainer
import ru.shanin.madarareddit.ui.main.mapper.UiModelsContainer.UiTopModel
import ru.shanin.madarareddit.ui.main.mapper.UiModelsContainer.UiTopWithoutImageModel

class ComplexDelegatesListAdapter(
    onLikeButtonClick: (item: UiModelsContainer) -> Unit,
    onOpenReddit: (item: UiModelsContainer) -> Unit
) : AsyncListDifferDelegationAdapter<UiModelsContainer>(ComplexDiffCallback()) {

    init {
        delegatesManager
            .addDelegate(ItemWithoutImageDelegate(onLikeButtonClick, onOpenReddit))
            .addDelegate(ComplexItemDelegate(onLikeButtonClick, onOpenReddit))
    }

    class ComplexDiffCallback : DiffUtil.ItemCallback<UiModelsContainer>() {
        override fun areItemsTheSame(first: UiModelsContainer, second: UiModelsContainer): Boolean {
            return first.javaClass == second.javaClass && when (first) {
                is UiTopModel -> first.id == (second as UiTopModel).id
                is UiTopWithoutImageModel -> first.id == (second as UiTopWithoutImageModel).id
            }
        }

        override fun areContentsTheSame(first: UiModelsContainer, second: UiModelsContainer): Boolean {
            return second == first
        }
    }
}
