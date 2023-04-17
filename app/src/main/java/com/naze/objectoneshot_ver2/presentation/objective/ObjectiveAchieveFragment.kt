package com.naze.objectoneshot_ver2.presentation.objective

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.naze.objectoneshot_ver2.R
import com.naze.objectoneshot_ver2.databinding.FragmentObjectiveAchieveListBinding
import com.naze.objectoneshot_ver2.domain.viewmodel.ObjectiveViewModel
import com.naze.objectoneshot_ver2.util.BindingFragment
import com.naze.objectoneshot_ver2.util.showToast

class ObjectiveAchieveFragment: BindingFragment<FragmentObjectiveAchieveListBinding>(R.layout.fragment_objective_achieve_list) {
    private val objectiveViewModel : ObjectiveViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val adapterObjective = ObjectiveAchieveListAdapter(ObjectiveAchieveListAdapter.ItemClickListener {
            requireContext().showToast("클릭")
        })
        binding.rvObjective.apply {
            adapter = adapterObjective
            layoutManager = LinearLayoutManager(requireContext())
        }
        objectiveViewModel.getObjectiveAchieveList()

        objectiveViewModel.objectiveListWithKeyResults.observe(viewLifecycleOwner) {
            adapterObjective.submitList(it)
        }
    }
}