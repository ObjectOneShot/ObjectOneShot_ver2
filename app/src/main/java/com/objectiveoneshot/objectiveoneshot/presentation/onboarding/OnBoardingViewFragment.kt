package com.objectiveoneshot.objectiveoneshot.presentation.onboarding

import android.os.Bundle
import android.view.View
import com.objectiveoneshot.objectiveoneshot.R
import com.objectiveoneshot.objectiveoneshot.databinding.FragmentOnboardingImageBinding
import com.objectiveoneshot.objectiveoneshot.util.BindingFragment

class OnBoardingViewFragment(private val drawable: Int): BindingFragment<FragmentOnboardingImageBinding>(R.layout.fragment_onboarding_image) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivOnboarding.setImageResource(drawable)
    }
}