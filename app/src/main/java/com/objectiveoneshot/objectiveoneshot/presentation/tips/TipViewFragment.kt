package com.objectiveoneshot.objectiveoneshot.presentation.tips

import android.os.Bundle
import android.view.View
import com.objectiveoneshot.objectiveoneshot.R
import com.objectiveoneshot.objectiveoneshot.databinding.FragmentTipImageBinding
import com.objectiveoneshot.objectiveoneshot.util.BindingFragment

class TipViewFragment(private val drawable: Int): BindingFragment<FragmentTipImageBinding>(R.layout.fragment_tip_image) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivTips.setImageResource(drawable)    }
}