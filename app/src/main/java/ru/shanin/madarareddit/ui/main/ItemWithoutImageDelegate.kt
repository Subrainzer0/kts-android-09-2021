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
        val itemView = ItemWithoutImageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(onLikeButtonClick, onItemClick, itemView)
    }

    override fun onBindViewHolder(item: Any, viewHolder: ViewHolder, payloads: MutableList<Any>) {
        viewHolder.bind(item as ItemWithoutImage)
    }

    inner class ViewHolder(
        private val onLikeButtonClick: (item: ItemWithoutImage) -> Unit,
        private val onItemClick: (item: ItemWithoutImage) -> Unit,
        private val binding: ItemWithoutImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemWithoutImage) {
            binding.apply {
                textContent.text = item.content
                subreddit.text = item.subreddit
                author.text = item.author
                uuid.text = item.uuid
                likeCounter.text = item.likeCounter.toString()
                imageButton.setOnClickListener {
                    onLikeButtonClick(item)
                }
                itemWithoutImage.setOnClickListener {
                    onItemClick(item)
                }
            }
        }
    }
}
