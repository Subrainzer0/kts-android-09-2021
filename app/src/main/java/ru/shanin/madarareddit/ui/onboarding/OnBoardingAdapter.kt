package ru.shanin.madarareddit.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingAdapter(
    activity: FragmentActivity,
    private val list: ArrayList<Fragment>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}
