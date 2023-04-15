package com.naze.objectoneshot_ver2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.naze.objectoneshot_ver2.databinding.ActivityMainBinding
import com.naze.objectoneshot_ver2.util.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ObjectOneShot_ver2)
        super.onCreate(savedInstanceState)
    }
}