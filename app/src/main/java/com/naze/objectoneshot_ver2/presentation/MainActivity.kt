package com.naze.objectoneshot_ver2.presentation

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.naze.objectoneshot_ver2.R
import com.naze.objectoneshot_ver2.databinding.ActivityMainBinding
import com.naze.objectoneshot_ver2.presentation.objective.ObjectiveListFragment
import com.naze.objectoneshot_ver2.util.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var imm: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ObjectOneShot_ver2)
        super.onCreate(savedInstanceState)
        setFragment(ObjectiveListFragment())

        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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
}