package com.naze.objectoneshot_ver2.presentation.objective

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.naze.objectoneshot_ver2.R
import com.naze.objectoneshot_ver2.databinding.FragmentObjectiveListBinding
import com.naze.objectoneshot_ver2.domain.viewmodel.ObjectiveViewModel
import com.naze.objectoneshot_ver2.util.BindingFragment
import com.naze.objectoneshot_ver2.util.SwipeHelper
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
                replace(R.id.fl_main, ObjectiveAchieveFragment(),"Achieve")
                addToBackStack(null)
                commit()
            }
        }
        setRecyclerView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setRecyclerView() {
        val adapterObjective = ObjectiveListAdapter(ObjectiveListAdapter.ItemClickListener {
            objectiveViewModel.getObjectiveData(it)
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fl_main, ObjectiveModifyFragment(), "ObjectiveModify")
                addToBackStack(null)
                commit()
            }
            //requireContext().showToast("클릭 $it")
        } ,objectiveViewModel)
        val swipeHelper = SwipeHelper().apply {
            setClamp(200f)
        }
        val itemTouchHelper = ItemTouchHelper(swipeHelper)
        itemTouchHelper.attachToRecyclerView(binding.rvObjective)

        binding.rvObjective.apply {
            adapter = adapterObjective
            layoutManager = LinearLayoutManager(requireContext())

            setOnTouchListener { _, _ ->
                swipeHelper.removePreviousClamp(this)
                false
            }
            (parent.parent as View).setOnTouchListener { v, event ->
                Log.d("Test_touch","반응2?")
                swipeHelper.removePreviousClamp(this)
                false
            }
        }

        objectiveViewModel.getObjectiveList()
        //가져오기

        objectiveViewModel.objectiveListWithKeyResults.observe(viewLifecycleOwner) {
            Log.d("TEST_observe","$it")
            if (it.isEmpty()) {
                binding.ivEmptyList.visibility = View.VISIBLE
                binding.tvEmptyList.visibility = View.VISIBLE
                binding.rvObjective.visibility = View.GONE
            } else {
                binding.ivEmptyList.visibility = View.GONE
                binding.tvEmptyList.visibility = View.GONE
                binding.rvObjective.visibility = View.VISIBLE
                adapterObjective.submitList(it)
            }

        }

        binding.srLayout.setOnRefreshListener {
            objectiveViewModel.getObjectiveList()
            binding.srLayout.isRefreshing = false
        }
    }

}