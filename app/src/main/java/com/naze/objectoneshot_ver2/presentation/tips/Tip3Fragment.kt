package com.naze.objectoneshot_ver2.presentation.tips

import android.os.Bundle
import android.util.Log
import android.view.View
import com.naze.objectoneshot_ver2.R
import com.naze.objectoneshot_ver2.databinding.FragmentTip1Binding
import com.naze.objectoneshot_ver2.databinding.FragmentTip3Binding
import com.naze.objectoneshot_ver2.util.BindingFragment

class Tip3Fragment: BindingFragment<FragmentTip3Binding>(R.layout.fragment_tip3) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TEST_swipe","3")
    }
}