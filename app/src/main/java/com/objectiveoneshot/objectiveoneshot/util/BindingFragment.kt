package com.objectiveoneshot.objectiveoneshot.util

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.objectiveoneshot.objectiveoneshot.domain.type.ItemId
import com.objectiveoneshot.objectiveoneshot.domain.type.ItemType

abstract class BindingFragment<B: ViewDataBinding>(@LayoutRes private val layoutRes: Int): Fragment() {
    private var _binding: B? = null
    val binding get() = requireNotNull(_binding!!) { "${this::class.java.simpleName}에서 에러가 발생했습니다."}

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)

        firebaseAnalytics = Firebase.analytics
        recordScreenView()
        return binding.root
    }

    fun analyticSelect(id: ItemId, name: ItemType) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            param(FirebaseAnalytics.Param.ITEM_ID, id.toString())
            param(FirebaseAnalytics.Param.ITEM_NAME,name.toString())
        }
    }

    private fun recordScreenView() {
        val screenName = this::class.java.simpleName
        Log.d("TEST_analytics","TEST $screenName")
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }
    }
}