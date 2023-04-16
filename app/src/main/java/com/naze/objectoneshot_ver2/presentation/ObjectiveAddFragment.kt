package com.naze.objectoneshot_ver2.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.naze.objectoneshot_ver2.R
import com.naze.objectoneshot_ver2.databinding.FragmentObjectiveAddBinding
import com.naze.objectoneshot_ver2.domain.viewmodel.ObjectiveViewModel
import com.naze.objectoneshot_ver2.util.BindingFragment
import com.naze.objectoneshot_ver2.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ObjectiveAddFragment: BindingFragment<FragmentObjectiveAddBinding>(R.layout.fragment_objective_add) {

    private val objectiveViewModel: ObjectiveViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewmodel = objectiveViewModel
        init()
    }

    private fun init() {
        setBtnEnable(false) //시작할 때 버튼 비활성화
        binding.etObjectiveName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                setBtnEnable(!s.isNullOrEmpty())
                //목표 등록하기 비활성화
                //KeyResult 추가 비활성화
            }
        })

        binding.btnAddObjective.setOnClickListener {
            objectiveViewModel.insertObjective()
            parentFragmentManager.popBackStackImmediate()
        } //Objective 등록

        setCalendar() //달력 설정
    }
    private fun setBtnEnable(isEnabled: Boolean) {
        binding.btnAddObjective.isEnabled = isEnabled
        binding.btnAddKeyResult.isEnabled = isEnabled
    }

    private fun setCalendar() {
        binding.btnObjectiveDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("기간을 선택해주세요")
                .build()
            datePicker.show(childFragmentManager, "date_picker")
            datePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance()
                objectiveViewModel.setObjectiveDateRange(
                    startDate = it.first ?: calendar.timeInMillis,
                    endDate = it.second ?: calendar.timeInMillis)
            }
        }
    }
    private fun addKeySetting () {

    }

}