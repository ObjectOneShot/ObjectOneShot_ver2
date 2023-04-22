package com.objectiveoneshot.objectiveoneshot.presentation.objective

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.objectiveoneshot.objectiveoneshot.R
import com.objectiveoneshot.objectiveoneshot.databinding.FragmentObjectiveListBinding
import com.objectiveoneshot.objectiveoneshot.domain.viewmodel.ObjectiveViewModel
import com.objectiveoneshot.objectiveoneshot.presentation.tips.TipsFragment
import com.objectiveoneshot.objectiveoneshot.util.BindingFragment
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
                dialog.setCancelable(true)
                dialog.setCanceledOnTouchOutside(true)
                dialog.findViewById<ImageFilterView>(R.id.dialog_view).setOnClickListener {
                    dialog.dismiss()
                }
                val handler = Handler()
                handler.postDelayed({
                    if(dialog.isShowing) {
                        dialog.dismiss()
                    }
                }, 5000)
                dialog.show()
            }
        }
        lifecycleScope.launch(Dispatchers.Main) {
            if ( objectiveViewModel.checkAchieveUnComplete()) {
                val dialog: Dialog = Dialog(requireContext())
                dialog.setContentView(R.layout.dialog_unachieve)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setCancelable(true)
                dialog.setCanceledOnTouchOutside(true)
                dialog.findViewById<ImageFilterView>(R.id.dialog_view).setOnClickListener {
                    dialog.dismiss()
                }
                val handler = Handler()
                handler.postDelayed({
                    if(dialog.isShowing) {
                        dialog.dismiss()
                    }
                }, 5000)
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