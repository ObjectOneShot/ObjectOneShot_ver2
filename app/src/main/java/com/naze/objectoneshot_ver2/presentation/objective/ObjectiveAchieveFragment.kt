package com.naze.objectoneshot_ver2.presentation.objective

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.naze.objectoneshot_ver2.R
import com.naze.objectoneshot_ver2.databinding.FragmentObjectiveAchieveListBinding
import com.naze.objectoneshot_ver2.domain.viewmodel.ObjectiveViewModel
import com.naze.objectoneshot_ver2.presentation.tips.TipsFragment
import com.naze.objectoneshot_ver2.util.BindingFragment

class ObjectiveAchieveFragment: BindingFragment<FragmentObjectiveAchieveListBinding>(R.layout.fragment_objective_achieve_list) {
    private val objectiveViewModel : ObjectiveViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()

        binding.btnHelp.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fl_main, TipsFragment(), "Tips")
                addToBackStack(null)
                commit()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setRecyclerView() {
        val adapterObjective = ObjectiveAchieveListAdapter(ObjectiveAchieveListAdapter.ItemClickListener {
             objectiveViewModel.getObjectiveData(it)
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fl_main, ObjectiveAchieveViewFragment(), "ObjectiveAchieveView")
                addToBackStack(null)
                commit()
            }
        },objectiveViewModel)

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