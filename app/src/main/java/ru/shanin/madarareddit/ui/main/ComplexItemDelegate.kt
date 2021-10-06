package ru.shanin.madarareddit.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.shanin.madarareddit.R
import ru.shanin.madarareddit.databinding.ItemComplexBinding
import ru.shanin.madarareddit.ui.main.mapper.UiModelsContainer
import ru.shanin.madarareddit.ui.main.mapper.UiModelsContainer.UiTopModel
import ru.shanin.madarareddit.utils.extensions.bindingInflate

class ComplexItemDelegate(
    private val onLikeButtonClick: (item: UiTopModel) -> Unit,
    private val onItemClick: (item: UiTopModel) -> Unit
) : AbsListItemAdapterDelegate<UiModelsContainer, UiModelsContainer, ComplexItemDelegate.ViewHolder>() {

    override fun isForViewType(item: UiModelsContainer, items: MutableList<UiModelsContainer>, position: Int): Boolean {
        return item is UiTopModel
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(onLikeButtonClick, onItemClick, parent.bindingInflate(ItemComplexBinding::inflate))
    }

    override fun onBindViewHolder(item: UiModelsContainer, viewHolder: ViewHolder, payloads: MutableList<Any>) {
        viewHolder.bind(item as UiTopModel)
    }

    inner class ViewHolder(
        private val onLikeButtonClick: (item: UiTopModel) -> Unit,
        private val onItemClick: (item: UiTopModel) -> Unit,
        private val binding: ItemComplexBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentItem: UiTopModel? = null

        init {
            binding.likeButton.setOnClickListener {
                currentItem?.let { item -> onLikeButtonClick(item) }
            }
            binding.complexItem.setOnClickListener {
                currentItem?.let { item -> onItemClick(item) }
            }
        }

        fun bind(item: UiTopModel) {
            currentItem = item
            binding.apply {
                textContent.text = item.title
                subreddit.text = item.subredditNamePrefixed
                uuid.text = item.id
                author.text = item.author
                likeCounter.text = item.score.toString()

                Glide.with(itemView)
                    .load(item.link)
                    .placeholder(R.drawable.ic_uchiha)
                    .into(binding.imageView)
            }
        }
    }
}
