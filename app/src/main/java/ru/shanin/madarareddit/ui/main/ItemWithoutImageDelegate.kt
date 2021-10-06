package ru.shanin.madarareddit.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.shanin.madarareddit.databinding.ItemWithoutImageBinding

class ItemWithoutImageDelegate(
    private val onLikeButtonClick: (item: ItemWithoutImage) -> Unit,
    private val onItemClick: (item: ItemWithoutImage) -> Unit
) : AbsListItemAdapterDelegate<Any, Any, ItemWithoutImageDelegate.ViewHolder>() {

    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean {
        return item is ItemWithoutImage
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val itemBinding = ItemWithoutImageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(onLikeButtonClick, onItemClick, itemBinding)
    }

    override fun onBindViewHolder(item: Any, viewHolder: ViewHolder, payloads: MutableList<Any>) {
        viewHolder.bind(item as ItemWithoutImage)
    }

    inner class ViewHolder(
        private val onLikeButtonClick: (item: ItemWithoutImage) -> Unit,
        private val onItemClick: (item: ItemWithoutImage) -> Unit,
        private val binding: ItemWithoutImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentItem: ItemWithoutImage? = null

        init {
            binding.imageButton.setOnClickListener {
                currentItem?.let { item -> onLikeButtonClick(item) }
            }
            binding.itemWithoutImage.setOnClickListener {
                currentItem?.let { item -> onItemClick(item) }
            }
        }

        fun bind(item: ItemWithoutImage) {
            currentItem = item
            binding.apply {
                textContent.text = item.content
                subreddit.text = item.subreddit
                uuid.text = item.uuid
                author.text = item.author
                likeCounter.text = item.likeCounter.toString()
            }
        }
    }
}
