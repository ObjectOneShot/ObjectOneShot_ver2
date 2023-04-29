package com.objectiveoneshot.objectiveoneshot.presentation.objective

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
import com.google.android.material.datepicker.MaterialDatePicker
import com.objectiveoneshot.objectiveoneshot.R
import com.objectiveoneshot.objectiveoneshot.databinding.FragmentObjectiveModifyBinding
import com.objectiveoneshot.objectiveoneshot.domain.type.KeyResultState
import com.objectiveoneshot.objectiveoneshot.domain.viewmodel.AppViewModel
import com.objectiveoneshot.objectiveoneshot.presentation.keyresult.KeyResultListFragment
import com.objectiveoneshot.objectiveoneshot.presentation.tips.TipsFragment
import com.objectiveoneshot.objectiveoneshot.util.BindingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ObjectiveModifyFragment: BindingFragment<FragmentObjectiveModifyBinding>(R.layout.fragment_objective_modify){

    private val viewModel : AppViewModel by activityViewModels()
    private var isCheck: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        init()
    }

    private fun init() {
        binding.etObjectiveName.addTextChangedListener(object : TextWatcher { //목표 삭제 시 빨간 줄
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {  }

            override fun afterTextChanged(s: Editable?) {
                binding.etObjectiveName.setHintTextColor(Color.parseColor("#FF808080"))
            }
        })
        setCalendar()
        if (isCheck) setFragment()
        setButton()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        })

        binding.btnHelp.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fl_main, TipsFragment(), "Tips")
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun setCalendar() {
        binding.btnObjectiveDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("기간을 선택해주세요")
                .build()
            datePicker.show(childFragmentManager, "date_picker")
            datePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance()
                viewModel.setObjectiveDateRange(
                    startDate = it.first ?: calendar.timeInMillis,
                    endDate = it.second ?: calendar.timeInMillis)
            }
        }
    }

    private fun setButton() {
        setAddKeyResult()
        setFragmentBtn()
        setTitleBarBtn()
    }

    /**  Add Key Result   */
    private fun setAddKeyResult () {
        binding.btnAddKeyResult.setOnClickListener { //Add btn 을 눌렀을 때
            if (!viewModel.checkKeyResultEmpty()) {
                viewModel.addKeyResult()
            }
        }
    }

    private fun setTitleBarBtn() {
        binding.toolBarBackBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private lateinit var transaction: FragmentTransaction
    private val fragment1 = KeyResultListFragment(KeyResultState.ALL)
    private val fragment2 = KeyResultListFragment(KeyResultState.ON_PROGRESS)
    private val fragment3 = KeyResultListFragment(KeyResultState.COMPLETE)

    private fun setFragment() {
        isCheck = false
        viewModel.setKeyResultState(KeyResultState.ALL)
        keyResultStateFragmentSetting()
        transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_key, fragment1)
        transaction.commit()
    }

    private fun keyResultStateFragmentSetting() {
        viewModel.keyResultState.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                when (it) {
                    KeyResultState.ALL -> {
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
            KeyResultState.ALL -> {
                transaction = childFragmentManager.beginTransaction().apply {
                    replace(R.id.fl_key, fragment1)
                    commit()
                }
            }
            KeyResultState.ON_PROGRESS -> {
                transaction = childFragmentManager.beginTransaction().apply {
                    replace(R.id.fl_key, fragment2)
                    commit()
                }
            }
            KeyResultState.COMPLETE -> {
                transaction = childFragmentManager.beginTransaction().apply {
                    replace(R.id.fl_key, fragment3)
                    commit()
                }
            }
        }
    }

    private fun setFragmentBtn() {
        binding.btnBeforeProgress.setOnClickListener {
            if (!it.isSelected) viewModel.setKeyResultState(KeyResultState.ALL)
        }
        binding.btnOnProgress.setOnClickListener {
            if (!it.isSelected) viewModel.setKeyResultState(KeyResultState.ON_PROGRESS)
        }
        binding.btnComplete.setOnClickListener {
            if (!it.isSelected) viewModel.setKeyResultState(KeyResultState.COMPLETE)
        }
    }

    fun onBackPressed() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            if (!viewModel.checkIsEmpty()) { //비어있는게 없을 때
                if (viewModel.checkIsChange()
                ) { //변경이 있으면 true
                    Log.d("TEST_Modify", "내용에 변경이 있어요")
                    val dialog = Dialog(requireContext())
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.setContentView(R.layout.dialog_save)
                    dialog.show()
                    dialog.findViewById<ImageFilterButton>(R.id.btn_save_dialog)
                        .setOnClickListener {
                            viewModel.updateObjectiveData()
                            dialog.dismiss()
                            parentFragmentManager.popBackStackImmediate()
                        }
                    dialog.findViewById<ImageFilterButton>(R.id.btn_cancel_dialog)
                        .setOnClickListener {
                            dialog.dismiss()
                            parentFragmentManager.popBackStackImmediate()
                        }
                } else {
                    Log.d("TEST_Modify", "내용에 변경이 없어요")
                    parentFragmentManager.popBackStackImmediate()
                }
            } else {
                val dialog = Dialog(requireContext())
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.dialog_task_alert)
                dialog.show()
            }
        }
    }
}