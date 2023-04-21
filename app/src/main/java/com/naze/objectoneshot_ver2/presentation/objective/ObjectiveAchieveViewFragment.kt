package com.naze.objectoneshot_ver2.presentation.objective

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.utils.widget.ImageFilterButton
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.naze.objectoneshot_ver2.R
import com.naze.objectoneshot_ver2.databinding.FragmentObjectiveAchieveListBinding
import com.naze.objectoneshot_ver2.databinding.FragmentObjectiveAchieveViewBinding
import com.naze.objectoneshot_ver2.databinding.FragmentObjectiveModifyBinding
import com.naze.objectoneshot_ver2.domain.type.KeyResultState
import com.naze.objectoneshot_ver2.domain.viewmodel.ObjectiveViewModel
import com.naze.objectoneshot_ver2.presentation.keyresult.KeyResultListFragment
import com.naze.objectoneshot_ver2.presentation.keyresult.KeyResultListUnEditFragment
import com.naze.objectoneshot_ver2.presentation.task.TaskAddAdapter
import com.naze.objectoneshot_ver2.util.BindingFragment
import com.naze.objectoneshot_ver2.util.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ObjectiveAchieveViewFragment: BindingFragment<FragmentObjectiveAchieveViewBinding>(R.layout.fragment_objective_achieve_view){

    private val objectiveViewModel : ObjectiveViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewmodel = objectiveViewModel
        init()
    }

    private fun init() {
        setFragment()
        setTitleBarBtn()
        setFragmentBtn()
    }

    private fun setTitleBarBtn() {
        binding.toolBarBackBtn.setOnClickListener {
            parentFragmentManager.popBackStackImmediate()
        }
    }

    private lateinit var transaction: FragmentTransaction
    private val fragment1 = KeyResultListUnEditFragment(KeyResultState.BEFORE_PROGRESS)
    private val fragment2 = KeyResultListUnEditFragment(KeyResultState.ON_PROGRESS)
    private val fragment3 = KeyResultListUnEditFragment(KeyResultState.COMPLETE)

    private fun setFragment() {
        objectiveViewModel.initKeyResultState()
        keyResultStateFragmentSetting()
        transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.fl_key, fragment1)
        transaction.add(R.id.fl_key, fragment2)
        transaction.add(R.id.fl_key, fragment3)
        transaction.commit()
    }

    private fun keyResultStateFragmentSetting() {
        objectiveViewModel.keyResultState.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                when (it) {
                    KeyResultState.BEFORE_PROGRESS -> {
                        binding.btnBeforeProgress.isSelected = true
                        binding.btnOnProgress.isSelected = false
                        binding.btnComplete.isSelected = false
                    }
                    KeyResultState.ON_PROGRESS -> {
                        binding.btnBeforeProgress.isSelected = false
                        binding.btnOnProgress.isSelected = true
                        binding.btnComplete.isSelected = false
                    }
                    KeyResultState.COMPLETE -> {
                        binding.btnBeforeProgress.isSelected = false
                        binding.btnOnProgress.isSelected = false
                        binding.btnComplete.isSelected = true
                    }
                }
                showFragment(it)
            }
        }
    }

    private fun showFragment(keyState: KeyResultState) {
        when (keyState) {
            KeyResultState.BEFORE_PROGRESS -> {
                transaction = childFragmentManager.beginTransaction().apply {
                    show(fragment1)
                    hide(fragment2)
                    hide(fragment3)
                    commit()
                }
            }
            KeyResultState.ON_PROGRESS -> {
                transaction = childFragmentManager.beginTransaction().apply {
                    hide(fragment1)
                    show(fragment2)
                    hide(fragment3)
                    commit()
                }
            }
            KeyResultState.COMPLETE -> {
                transaction = childFragmentManager.beginTransaction().apply {
                    hide(fragment1)
                    hide(fragment2)
                    show(fragment3)
                    commit()
                }
            }
        }
    }

    private fun setFragmentBtn() {
        binding.btnBeforeProgress.setOnClickListener {
            if (!it.isSelected) objectiveViewModel.setKeyResultState(KeyResultState.BEFORE_PROGRESS)
        }
        binding.btnOnProgress.setOnClickListener {
            if (!it.isSelected) objectiveViewModel.setKeyResultState(KeyResultState.ON_PROGRESS)
        }
        binding.btnComplete.setOnClickListener {
            if (!it.isSelected) objectiveViewModel.setKeyResultState(KeyResultState.COMPLETE)
        }
    }
}