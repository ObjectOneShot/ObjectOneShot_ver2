package com.naze.objectoneshot_ver2.presentation.tips

import android.os.Bundle
import android.view.View
import com.naze.objectoneshot_ver2.R
import com.naze.objectoneshot_ver2.databinding.FragmentTipImageBinding
import com.naze.objectoneshot_ver2.util.BindingFragment

class TipViewFragment(private val drawable: Int): BindingFragment<FragmentTipImageBinding>(R.layout.fragment_tip_image) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivTips.setImageResource(drawable)    }
}