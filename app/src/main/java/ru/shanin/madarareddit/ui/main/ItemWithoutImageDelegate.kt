package ru.shanin.madarareddit.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.shanin.madarareddit.databinding.ItemWithoutImageBinding
import ru.shanin.madarareddit.ui.main.mapper.UiModelsContainer
import ru.shanin.madarareddit.ui.main.mapper.UiModelsContainer.UiTopWithoutImageModel
import ru.shanin.madarareddit.utils.extensions.bindingInflate

class ItemWithoutImageDelegate(
    private val onLikeButtonClick: (item: UiTopWithoutImageModel) -> Unit,
    private val onItemClick: (item: UiTopWithoutImageModel) -> Unit
) : AbsListItemAdapterDelegate<UiModelsContainer, UiModelsContainer, ItemWithoutImageDelegate.ViewHolder>() {

    override fun isForViewType(
        item: UiModelsContainer,
        items: MutableList<UiModelsContainer>,
        position: Int
    ): Boolean {
        return item is UiTopWithoutImageModel
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(onLikeButtonClick, onItemClick, parent.bindingInflate(ItemWithoutImageBinding::inflate))
    }

    override fun onBindViewHolder(item: UiModelsContainer, viewHolder: ViewHolder, payloads: MutableList<Any>) {
        viewHolder.bind(item as UiTopWithoutImageModel)
    }

    inner class ViewHolder(
        private val onLikeButtonClick: (item: UiTopWithoutImageModel) -> Unit,
        private val onItemClick: (item: UiTopWithoutImageModel) -> Unit,
        private val binding: ItemWithoutImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UiTopWithoutImageModel) {
            binding.apply {
                textContent.text = item.title
                subreddit.text = item.subredditNamePrefixed
                author.text = item.author
                uuid.text = item.id
                likeCounter.text = item.score.toString()

                likeButton.setOnClickListener {
                    onLikeButtonClick(item)
                }
                itemWithoutImage.setOnClickListener {
                    onItemClick(item)
                }
            }
        }
    }
}
