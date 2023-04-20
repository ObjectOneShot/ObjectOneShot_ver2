package com.naze.objectoneshot_ver2.presentation.onboarding

import android.os.Bundle
import android.view.View
import com.naze.objectoneshot_ver2.R
import com.naze.objectoneshot_ver2.databinding.FragmentOnboardingImageBinding
import com.naze.objectoneshot_ver2.util.BindingFragment

class OnBoardingViewFragment(private val drawable: Int): BindingFragment<FragmentOnboardingImageBinding>(R.layout.fragment_onboarding_image) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivOnboarding.setImageResource(drawable)
    }
}