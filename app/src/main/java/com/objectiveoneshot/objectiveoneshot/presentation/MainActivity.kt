package com.objectiveoneshot.objectiveoneshot.presentation

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.objectiveoneshot.objectiveoneshot.R
import com.objectiveoneshot.objectiveoneshot.databinding.ActivityMainBinding
import com.objectiveoneshot.objectiveoneshot.presentation.objective.ObjectiveListFragment
import com.objectiveoneshot.objectiveoneshot.presentation.onboarding.OnBoardingFragment
import com.objectiveoneshot.objectiveoneshot.util.BindingActivity
import com.objectiveoneshot.objectiveoneshot.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var imm: InputMethodManager
    private var backPressedTime: Long = 0

    companion object {
        lateinit var sharedPref: SharedPreferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ObjectOneShot_ver2)
        super.onCreate(savedInstanceState)

        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        if (sharedPref.getBoolean("isFirstRun", true)) {
            showOnBoarding()
        } else {
            setFragment(ObjectiveListFragment())
        }
    }

    private fun showOnBoarding() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_main, OnBoardingFragment(), "OnBoarding" )
        transaction.commit()
    }

    private fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_main, fragment, "ObjectiveList")
        transaction.commit()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onBackPressed() {
        for (fragment: Fragment in supportFragmentManager.fragments) {
            when (fragment.tag) {
                "ObjectiveList" -> {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - backPressedTime < 2000) {
                        super.onBackPressed()
                        finish()
                    } else {
                        backPressedTime = currentTime
                        this.showToast("한 번 더 누르면 종료됩니다.")
                    }
                }
                "ObjectiveModify" -> {
                    Log.d("TEST_MAIN","onBackPressed $fragment")
                    super.onBackPressed()
                }
                "ObjectiveAdd" -> {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - backPressedTime < 2000) {
                        supportFragmentManager.popBackStackImmediate()
                    } else {
                        backPressedTime = currentTime
                        this.showToast("한 번 더 누르면 페이지에서 나갑니다.")
                    }
                }
                else -> {
                    supportFragmentManager.popBackStackImmediate()
                }
            }
        }

    }
}