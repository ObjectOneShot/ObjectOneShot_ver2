package com.naze.objectoneshot_ver2.presentation.tips

import android.os.Bundle
import android.util.Log
import android.view.View
import com.naze.objectoneshot_ver2.R
import com.naze.objectoneshot_ver2.databinding.FragmentTip1Binding
import com.naze.objectoneshot_ver2.util.BindingFragment

class Tip1Fragment: BindingFragment<FragmentTip1Binding>(R.layout.fragment_tip1) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TEST_swipe","1")
    }
}