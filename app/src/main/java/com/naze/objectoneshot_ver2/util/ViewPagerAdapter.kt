package com.naze.objectoneshot_ver2.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment,private val arrayList: ArrayList<Fragment>): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun createFragment(position: Int): Fragment {
        return arrayList[position]
    }
}