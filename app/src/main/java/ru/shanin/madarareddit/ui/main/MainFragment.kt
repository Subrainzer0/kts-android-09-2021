package ru.shanin.madarareddit.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.shanin.madarareddit.R
import ru.shanin.madarareddit.databinding.FragmentMainBinding
import ru.shanin.madarareddit.utils.PaginationScrollListener
import ru.shanin.madarareddit.utils.autoCleared
import timber.log.Timber
import java.util.UUID


class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding()
    private var complexAdapter: ComplexDelegatesListAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        loadMoreItems()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initList() {
        complexAdapter = ComplexDelegatesListAdapter(
            onLikeButtonClick = { item ->
                when (item) {
                    is ItemWithoutImage -> {
                        item.likeCounter += 1
                    }
                    is ComplexItem -> {
                        item.likeCounter += 1
                    }
                }
                complexAdapter.notifyDataSetChanged()
            },
            onOpenReddit = { openRedditLink() }
        )

        with(binding.list) {
            val orientation = RecyclerView.VERTICAL
            adapter = complexAdapter
            layoutManager = LinearLayoutManager(context, orientation, false)

            addOnScrollListener(
                PaginationScrollListener(
                    layoutManager = layoutManager as LinearLayoutManager,
                    requestNextItems = ::loadMoreItems,
                    visibilityThreshold = 10
                )
            )

            addItemDecoration(DividerItemDecoration(context, orientation))
            setHasFixedSize(true)
        }
    }

    private fun getDefaultItems() = List(20) {
        val uuid = UUID.randomUUID().toString()

        when ((1..2).random()) {
            1 -> ComplexItem(
                subreddit = "/r/overview",
                author = "Vasya Pupich",
                content = "Васек пошел гулять куда то там далеко",
                likeCounter = 0,
                uuid = uuid
            )
            2 -> ItemWithoutImage(
                subreddit = "/r/overview",
                author = "Varan Nurick",
                content = "Зачем Нурик написал код на async task",
                likeCounter = 0,
                uuid = uuid
            )
            else -> error("Wrong random number")
        }
    }

    private fun loadMoreItems() {
        val newItems = complexAdapter.items + getDefaultItems()
        complexAdapter.items = newItems
        Timber.d(newItems.size.toString())
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openRedditLink() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://www.reddit.com")
        }

        val packageManager = requireActivity().packageManager
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
        else {
            Timber.d("No Intent available to handle action")
        }
    }
}
