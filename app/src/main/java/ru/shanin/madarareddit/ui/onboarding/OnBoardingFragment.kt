package ru.shanin.madarareddit.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.shanin.madarareddit.R.layout
import ru.shanin.madarareddit.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment(layout.fragment_on_boarding) {

    private val binding: FragmentOnBoardingBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextButton.setOnClickListener {
            val action = OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }
}
