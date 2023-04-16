package com.naze.objectoneshot_ver2.presentation.objective

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.naze.objectoneshot_ver2.R
import com.naze.objectoneshot_ver2.databinding.FragmentObjectiveListBinding
import com.naze.objectoneshot_ver2.domain.viewmodel.ObjectiveViewModel
import com.naze.objectoneshot_ver2.util.BindingFragment
import com.naze.objectoneshot_ver2.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ObjectiveListFragment: BindingFragment<FragmentObjectiveListBinding>(R.layout.fragment_objective_list) {
    private val objectiveViewModel: ObjectiveViewModel by activityViewModels()

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
        binding.btnAchieve.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {

            }
        }
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val adapterObjective = ObjectiveListAdapter(ObjectiveListAdapter.ItemClickListener {
/*            parentFragmentManager.beginTransaction().apply {
                objectiveViewModel.getModifyObjective(it)
                replace(R.id.fl_main, ObjectiveModifyFragment(),"KeyResultModify")
                addToBackStack(null)
                commit()
            }*/
            //TODO(미구현)
            requireContext().showToast("클릭")
        } )

        binding.rvObjective.apply {
            adapter = adapterObjective
            layoutManager = LinearLayoutManager(requireContext())
        }
        objectiveViewModel.getObjectiveList()
        //가져오기

        objectiveViewModel.objectiveList.observe(viewLifecycleOwner) {
            adapterObjective.submitList(it)
        }

        binding.srLayout.setOnRefreshListener {
            objectiveViewModel.getObjectiveList()
            binding.srLayout.isRefreshing = false
        }
    }

}