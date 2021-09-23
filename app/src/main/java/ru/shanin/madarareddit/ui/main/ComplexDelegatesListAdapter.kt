package ru.shanin.madarareddit.ui.main

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.shanin.madarareddit.databinding.ItemComplexBinding
import ru.shanin.madarareddit.databinding.ItemWithoutImageBinding

class ComplexDelegatesListAdapter(
    onLikeButtonClick: (item: Any) -> Unit,
    onOpenReddit: (item: Any) -> Unit
) : AsyncListDifferDelegationAdapter<Any>(ComplexDiffCallback()) {

    init {
        delegatesManager
            .addDelegate(ItemWithoutImageDelegate(onLikeButtonClick, onOpenReddit))
            .addDelegate(ComplexItemDelegate(onLikeButtonClick, onOpenReddit))
    }

    class ComplexDiffCallback : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(first: Any, second: Any): Boolean {
            return first.javaClass == second.javaClass && when (first) {
                is ItemComplexBinding -> first.uuid == (second as ItemComplexBinding).uuid
                is ItemWithoutImageBinding -> first.uuid == (second as ItemWithoutImageBinding).uuid
                else -> true
            }
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(first: Any, second: Any): Boolean = first == second
    }
}
