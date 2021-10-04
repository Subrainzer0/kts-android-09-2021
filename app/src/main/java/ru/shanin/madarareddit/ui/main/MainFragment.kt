package ru.shanin.madarareddit.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.shanin.madarareddit.R
import ru.shanin.madarareddit.databinding.FragmentMainBinding
import ru.shanin.madarareddit.ui.main.mapper.UiModelsContainer
import ru.shanin.madarareddit.ui.main.mapper.UiModelsContainer.UiTopModel
import ru.shanin.madarareddit.ui.main.mapper.UiModelsContainer.UiTopWithoutImageModel
import ru.shanin.madarareddit.utils.PaginationScrollListener
import ru.shanin.madarareddit.utils.autoCleared
import timber.log.Timber

class MainFragment : Fragment(R.layout.fragment_main) {

    private val mainViewModel: MainViewModel by viewModels()

    private var startedTopList: List<UiModelsContainer> = (emptyList())
    private var loadTopList: List<UiModelsContainer> = (emptyList())
    private var fullTopList: List<UiModelsContainer> = (emptyList())

    private val binding: FragmentMainBinding by viewBinding()
    private var complexAdapter: ComplexDelegatesListAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindViewModel()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initList() {
        complexAdapter = ComplexDelegatesListAdapter(
            onLikeButtonClick = { item ->
                updateVoting(item)
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
                )
            )

            addItemDecoration(DividerItemDecoration(context, orientation))
            setHasFixedSize(true)
        }
    }

    private fun bindViewModel() {
        mainViewModel.topList.observe(viewLifecycleOwner, {
            startedTopList = it
            complexAdapter.items = startedTopList
            loadTopList = it
            fullTopList = it
        })

        mainViewModel.isLoading.observe(viewLifecycleOwner, { enableControls(it.not()) })

        mainViewModel.isError.observe(viewLifecycleOwner, { isError ->
            if (isError) {
                showError()
            }
        })

        mainViewModel.loadedTopList.observe(viewLifecycleOwner, { loadedTopList ->
            if (loadTopList == loadedTopList) {
                fullTopList = loadedTopList
                complexAdapter.items = fullTopList
            }
            else {
                loadTopList = loadedTopList
                fullTopList = startedTopList + loadTopList
                complexAdapter.items = fullTopList
            }
        })

        mainViewModel.getTop()
    }

    private fun enableControls(enable: Boolean) = with(binding) {
        list.isVisible = enable
        shimmerLayout.isVisible = !enable
    }

    private fun showError() = with(binding) {
        list.isVisible = false
        shimmerLayout.isVisible = false
        createAlertDialog()?.show()
    }

    private fun createAlertDialog(): AlertDialog? {
        val alertDialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("retry") { _, _ ->
                    loadMoreItems()
                }
                setNegativeButton("exit") { _, _ ->
                    activity?.finish()
                }
            }

            builder.create()
        }

        return alertDialog
    }

    private fun updateVoting(item: UiModelsContainer) {
        when (item) {
            is UiTopModel -> {
                when (item.isLiked) {
                    true -> {
                        item.isLiked = false
                        item.score -= 1
                        mainViewModel.vote(item.name, "0")
                    }
                    false -> {
                        item.isLiked = true
                        item.score += 1
                        mainViewModel.vote(item.name, "1")
                    }
                    else -> {
                        item.score += 1
                        mainViewModel.vote(item.name, "1")
                    }
                }
            }
            is UiTopWithoutImageModel -> {
                when (item.isLiked) {
                    true -> {
                        item.isLiked = false
                        item.score -= 1
                        mainViewModel.vote(item.name, "0")
                    }
                    false -> {
                        item.isLiked = true
                        item.score += 1
                        mainViewModel.vote(item.name, "1")
                    }
                    else -> {
                        item.score += 1
                        mainViewModel.vote(item.name, "1")
                    }
                }
            }
        }
    }

    private fun loadMoreItems() {
        if (fullTopList.isNotEmpty()) {
            val lastId = when (val lastItem = fullTopList.last()) {
                is UiTopWithoutImageModel -> lastItem.name
                is UiTopModel -> lastItem.name
            }

            mainViewModel.getTopWithIndex(lastId)
        }
        else {
            mainViewModel.getTop()
        }
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
