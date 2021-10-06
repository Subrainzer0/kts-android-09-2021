package ru.shanin.madarareddit.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.shanin.madarareddit.R
import ru.shanin.madarareddit.databinding.FragmentOnBoardingBinding
import ru.shanin.madarareddit.ui.onboarding.viewpagerscreens.FirstScreen
import ru.shanin.madarareddit.ui.onboarding.viewpagerscreens.FirthScreen
import ru.shanin.madarareddit.ui.onboarding.viewpagerscreens.SecondScreen

class OnBoardingFragment : Fragment(R.layout.fragment_on_boarding) {

    private val binding: FragmentOnBoardingBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pager = binding.onBoardingViewPager

        val listOfFragments = arrayListOf(
            FirstScreen(),
            SecondScreen(),
            FirthScreen()
        )

        val onBoardingAdapter = OnBoardingAdapter(requireActivity(), listOfFragments)
        binding.onBoardingViewPager.adapter = onBoardingAdapter
        binding.dotsIndicator.setViewPager2(pager)

    }
}
