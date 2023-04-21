package com.objectiveoneshot.objectiveoneshot.presentation.tips

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.objectiveoneshot.objectiveoneshot.R
import com.objectiveoneshot.objectiveoneshot.databinding.FragmentViewpagerBinding
import com.objectiveoneshot.objectiveoneshot.util.BindingFragment
import com.objectiveoneshot.objectiveoneshot.util.ViewPagerAdapter

class TipsFragment: BindingFragment<FragmentViewpagerBinding>(R.layout.fragment_viewpager) {

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerAdapter = ViewPagerAdapter(this, getTipsList())
        viewPager = binding.viewPager
        viewPager.adapter = viewPagerAdapter
        binding.springDotsIndicator.attachTo(viewPager)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == viewPagerAdapter.itemCount - 1) {
                    binding.btnSkip.visibility = View.GONE
                    binding.btnStart.visibility = View.VISIBLE
                } else {
                    binding.btnSkip.visibility = View.VISIBLE
                    binding.btnStart.visibility = View.GONE
                }
            }
        })

        binding.btnSkip.setOnClickListener {
            parentFragmentManager.popBackStackImmediate()
        }

        binding.btnStart.setOnClickListener {
            parentFragmentManager.popBackStackImmediate()
        }
    }

    private fun getTipsList(): ArrayList<Fragment> {
        return arrayListOf(
            TipViewFragment(R.drawable.image_tip1),
            TipViewFragment(R.drawable.image_tip2),
            TipViewFragment(R.drawable.image_tip3),
            TipViewFragment(R.drawable.image_tip4),)
    }
}