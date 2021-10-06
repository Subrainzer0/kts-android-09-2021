package ru.shanin.madarareddit.ui.onboarding.viewpagerscreens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.shanin.madarareddit.R
import ru.shanin.madarareddit.databinding.FragmentFirthBinding
import ru.shanin.madarareddit.ui.onboarding.OnBoardingFragmentDirections

class FirthScreen : Fragment(R.layout.fragment_firth) {

    private val binding: FragmentFirthBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextButton.setOnClickListener {
            val action = OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }
}
