package com.naze.objectoneshot_ver2.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.naze.objectoneshot_ver2.R
import com.naze.objectoneshot_ver2.databinding.FragmentObjectiveListBinding
import com.naze.objectoneshot_ver2.domain.viewmodel.KeyResultViewModel
import com.naze.objectoneshot_ver2.domain.viewmodel.ObjectiveViewModel
import com.naze.objectoneshot_ver2.util.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ObjectiveListFragment: BindingFragment<FragmentObjectiveListBinding>(R.layout.fragment_objective_list) {
    private val objectiveViewModel: ObjectiveViewModel by activityViewModels()
    private val keyResultViewModel: KeyResultViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        binding.btnAddObjective.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                objectiveViewModel.initObjectiveData() //ObjectiveData 빈 값 생성
                replace(R.id.fl_main, ObjectiveAddFragment(),"ObjectiveAdd")
                addToBackStack(null)
                commit()
            }
        }
    }

}