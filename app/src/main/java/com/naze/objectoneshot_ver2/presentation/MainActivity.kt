package com.naze.objectoneshot_ver2.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.naze.objectoneshot_ver2.R
import com.naze.objectoneshot_ver2.databinding.ActivityMainBinding
import com.naze.objectoneshot_ver2.util.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ObjectOneShot_ver2)
        super.onCreate(savedInstanceState)
        setFragment(ObjectiveListFragment())
    }

    private fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_main, fragment, "ObjectiveList")
        transaction.commit()
    }
}