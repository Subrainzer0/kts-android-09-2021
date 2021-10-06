package ru.shanin.madarareddit.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.shanin.madarareddit.databinding.ItemComplexBinding

class ComplexItemDelegate(
    private val onLikeButtonClick: (item: ComplexItem) -> Unit,
    private val onItemClick: (item: ComplexItem) -> Unit
) : AbsListItemAdapterDelegate<Any, Any, ComplexItemDelegate.ViewHolder>() {

    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean {
        return item is ComplexItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val itemBinding = ItemComplexBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(onLikeButtonClick, onItemClick, itemBinding)
    }

    override fun onBindViewHolder(item: Any, viewHolder: ViewHolder, payloads: MutableList<Any>) {
        viewHolder.bind(item as ComplexItem)
    }

    inner class ViewHolder(
        private val onLikeButtonClick: (item: ComplexItem) -> Unit,
        private val onItemClick: (item: ComplexItem) -> Unit,
        private val binding: ItemComplexBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentItem: ComplexItem? = null

        init {
            binding.imageButton.setOnClickListener {
                currentItem?.let { item -> onLikeButtonClick(item) }
            }
            binding.complexItem.setOnClickListener {
                currentItem?.let { item -> onItemClick(item) }
            }
        }

        fun bind(item: ComplexItem) {
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
