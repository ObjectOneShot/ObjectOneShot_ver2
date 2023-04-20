package com.objectiveoneshot.objectiveoneshot.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.objectiveoneshot.objectiveoneshot.R
import com.objectiveoneshot.objectiveoneshot.databinding.FragmentViewpagerBinding
import com.objectiveoneshot.objectiveoneshot.presentation.MainActivity.Companion.sharedPref
import com.objectiveoneshot.objectiveoneshot.presentation.objective.ObjectiveListFragment
import com.objectiveoneshot.objectiveoneshot.util.BindingFragment
import com.objectiveoneshot.objectiveoneshot.util.ViewPagerAdapter

class OnBoardingFragment: BindingFragment<FragmentViewpagerBinding>(R.layout.fragment_viewpager) {

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
            parentFragmentManager.beginTransaction().apply {
                sharedPref.edit().putBoolean("isFirstRun", false).apply()
                replace(R.id.fl_main, ObjectiveListFragment(), "ObjectiveList")
                commit()
            }
        }

        binding.btnStart.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                sharedPref.edit().putBoolean("isFirstRun", false).apply()
                replace(R.id.fl_main, ObjectiveListFragment(), "ObjectiveList")
                commit()
            }
        }
    }

    private fun getTipsList(): ArrayList<Fragment> {
        return arrayListOf(
            OnBoardingViewFragment(R.drawable.image_onboarding1),
            OnBoardingViewFragment(R.drawable.image_onboarding2),
            OnBoardingViewFragment(R.drawable.image_onboarding3),
            OnBoardingViewFragment(R.drawable.image_onboarding4),
            OnBoardingViewFragment(R.drawable.image_onboarding5),)
    }
}