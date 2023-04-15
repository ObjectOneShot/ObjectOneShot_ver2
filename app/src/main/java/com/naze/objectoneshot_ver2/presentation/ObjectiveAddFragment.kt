package com.naze.objectoneshot_ver2.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.naze.objectoneshot_ver2.R
import com.naze.objectoneshot_ver2.databinding.FragmentObjectiveAddBinding
import com.naze.objectoneshot_ver2.domain.viewmodel.ObjectiveViewModel
import com.naze.objectoneshot_ver2.util.BindingFragment
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
        binding.etObjectiveName.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                objectiveViewModel.setObjectiveTitle(binding.etObjectiveName.text.toString())
            }
        }
        binding
    }

}