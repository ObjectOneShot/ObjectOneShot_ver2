package com.naze.objectoneshot_ver2.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.naze.objectoneshot_ver2.R
import com.naze.objectoneshot_ver2.databinding.FragmentObjectiveAddBinding
import com.naze.objectoneshot_ver2.domain.viewmodel.ObjectiveViewModel
import com.naze.objectoneshot_ver2.util.BindingFragment
import com.naze.objectoneshot_ver2.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ObjectiveAddFragment: BindingFragment<FragmentObjectiveAddBinding>(R.layout.fragment_objective_add) {

    private val objectiveViewModel: ObjectiveViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.objective = objectiveViewModel.objective.value

        init()
    }

    private fun init() {
        binding.btnAddObjective.setOnClickListener {
            objectiveViewModel.insertObjective()
        }
/*        binding.btnObjectiveDate.setOnClickListener {
            requireContext().showToast("테스트")
            //editText에 focus 있을 때, 다른 위치 터치 시 키보드 종료 but 해당 위치에 touchEvent가 있을 경우 해당 touchEvent 실행 용 테스트 코드
        }*/
    }

}