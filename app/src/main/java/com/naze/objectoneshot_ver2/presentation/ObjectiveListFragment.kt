package com.naze.objectoneshot_ver2.presentation

import androidx.fragment.app.activityViewModels
import com.naze.objectoneshot_ver2.R
import com.naze.objectoneshot_ver2.databinding.FragmentObjectiveListBinding
import com.naze.objectoneshot_ver2.domain.viewmodel.KeyResultViewModel
import com.naze.objectoneshot_ver2.domain.viewmodel.ObjectiveViewModel
import com.naze.objectoneshot_ver2.util.BindingFragment

class ObjectiveListFragment: BindingFragment<FragmentObjectiveListBinding>(R.layout.fragment_objective_list) {
    private val objectiveViewModel: ObjectiveViewModel by activityViewModels()
    private val keyResultViewModel: KeyResultViewModel by activityViewModels()

}