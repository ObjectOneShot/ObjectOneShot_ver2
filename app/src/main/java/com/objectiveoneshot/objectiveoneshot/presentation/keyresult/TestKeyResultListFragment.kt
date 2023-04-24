package com.objectiveoneshot.objectiveoneshot.presentation.keyresult

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.objectiveoneshot.objectiveoneshot.R
import com.objectiveoneshot.objectiveoneshot.databinding.TestFragmentKeyResultListBinding
import com.objectiveoneshot.objectiveoneshot.domain.type.KeyResultState
import com.objectiveoneshot.objectiveoneshot.domain.viewmodel.AppViewModel
import com.objectiveoneshot.objectiveoneshot.util.BindingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestKeyResultListFragment(val state: KeyResultState): BindingFragment<TestFragmentKeyResultListBinding>(R.layout.test_fragment_key_result_list) {
    private val viewModel: AppViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setList()
    }

    private fun setList() {
        val adapterKeyResult = TestKeyResultAdapter(viewModel)

        binding.rvKeyList.apply {
            adapter = adapterKeyResult
            layoutManager = LinearLayoutManager(requireContext())
        }

        when (state) {
            KeyResultState.BEFORE_PROGRESS -> {
                viewModel.keyResultWithTasks.observe(viewLifecycleOwner) { item ->
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                        adapterKeyResult.submitList(item?.filter { it.keyResult.progress <= 0.0 })
                    }
                }
            }
            KeyResultState.ON_PROGRESS -> {
                viewModel.keyResultWithTasks.observe(viewLifecycleOwner) { item ->
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                        adapterKeyResult.submitList(item?.filter { it.keyResult.progress in 1.0..99.0 })
                    }
                }
            }
            KeyResultState.COMPLETE -> {
                viewModel.keyResultWithTasks.observe(viewLifecycleOwner) { item ->
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                        adapterKeyResult.submitList(item?.filter { it.keyResult.progress >= 100.0 })
                    }
                }
            }
        }
    }
}