package com.objectiveoneshot.objectiveoneshot.presentation.keyresult

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.objectiveoneshot.objectiveoneshot.R
import com.objectiveoneshot.objectiveoneshot.databinding.FragmentKeyResultListBinding
import com.objectiveoneshot.objectiveoneshot.domain.type.KeyResultState
import com.objectiveoneshot.objectiveoneshot.domain.viewmodel.ObjectiveViewModel
import com.objectiveoneshot.objectiveoneshot.util.BindingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KeyResultListFragment(val state: KeyResultState): BindingFragment<FragmentKeyResultListBinding>(R.layout.fragment_key_result_list) {
    private val objectiveViewModel: ObjectiveViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setList()
    }

    private fun setList() {
        val adapterKeyResult = KeyResultAdapter(objectiveViewModel)

        binding.rvKeyList.apply {
            adapter = adapterKeyResult
            layoutManager = LinearLayoutManager(requireContext())
        }

        when (state) {
            KeyResultState.BEFORE_PROGRESS -> {
                objectiveViewModel.keyResultList.observe(viewLifecycleOwner) { item ->
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                        adapterKeyResult.submitList(item?.filter { it.progress <= 0.0 })
                    }
                }
            }
            KeyResultState.ON_PROGRESS -> {
                objectiveViewModel.keyResultList.observe(viewLifecycleOwner) { item ->
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                        adapterKeyResult.submitList(item?.filter { it.progress in 1.0..99.0 })
                    }
                }
            }
            KeyResultState.COMPLETE -> {
                objectiveViewModel.keyResultList.observe(viewLifecycleOwner) { item ->
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                        adapterKeyResult.submitList(item?.filter { it.progress >= 100.0 })
                    }
                }
            }
        }
    }
}