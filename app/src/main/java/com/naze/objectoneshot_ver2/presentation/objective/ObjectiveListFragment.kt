package com.naze.objectoneshot_ver2.presentation.objective

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.naze.objectoneshot_ver2.R
import com.naze.objectoneshot_ver2.databinding.FragmentObjectiveListBinding
import com.naze.objectoneshot_ver2.domain.viewmodel.ObjectiveViewModel
import com.naze.objectoneshot_ver2.presentation.tips.TipsFragment
import com.naze.objectoneshot_ver2.util.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ObjectiveListFragment: BindingFragment<FragmentObjectiveListBinding>(R.layout.fragment_objective_list) {
    private val objectiveViewModel: ObjectiveViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun setAchieveDialog() {
        lifecycleScope.launch(Dispatchers.Main) {
            if( objectiveViewModel.checkAchieveComplete() ) {
                val dialog: Dialog = Dialog(requireContext())
                dialog.setContentView(R.layout.dialog_achieve)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
            }
        }
        lifecycleScope.launch(Dispatchers.Main) {
            if ( objectiveViewModel.checkAchieveUnComplete()) {
                val dialog: Dialog = Dialog(requireContext())
                dialog.setContentView(R.layout.dialog_unachieve)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
            }
        }

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
        binding.btnHelp.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fl_main, TipsFragment(), "Tips")
                addToBackStack(null)
                commit()
            }
        }
        setRecyclerView()
        setAchieveDialog()
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
        } ,objectiveViewModel)


        binding.rvObjective.apply {
            adapter = adapterObjective
            layoutManager = LinearLayoutManager(requireContext())
        }

        objectiveViewModel.getObjectiveList()
        //가져오기

        objectiveViewModel.objectiveListWithKeyResults.observe(viewLifecycleOwner) {
            Log.d("TEST_observe","$it")
            if (it.isEmpty()) {
                binding.ivEmptyList.visibility = View.VISIBLE
                binding.tvEmptyList.visibility = View.VISIBLE
                adapterObjective.submitList(it)
            } else {
                binding.ivEmptyList.visibility = View.GONE
                binding.tvEmptyList.visibility = View.GONE
                adapterObjective.submitList(it)
            }
        }

        binding.srLayout.setOnRefreshListener {
            objectiveViewModel.getObjectiveList()
            binding.srLayout.isRefreshing = false
        }
    }

}