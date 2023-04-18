package com.naze.objectoneshot_ver2.presentation.objective

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.naze.objectoneshot_ver2.data.local.model.Objective
import com.naze.objectoneshot_ver2.data.local.model.ObjectiveWithKeyResults
import com.naze.objectoneshot_ver2.databinding.ItemObjectiveAchieveBinding
import com.naze.objectoneshot_ver2.domain.viewmodel.ObjectiveViewModel
import com.naze.objectoneshot_ver2.util.ItemDiffCallback

class ObjectiveAchieveListAdapter(
    private val clickListener: ItemClickListener,
    private val objectiveViewModel: ObjectiveViewModel
): ListAdapter<ObjectiveWithKeyResults, RecyclerView.ViewHolder>(
ItemDiffCallback<ObjectiveWithKeyResults>(
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.objective.id == new.objective.id}
    ))
 {
     override fun onCreateViewHolder(
         parent: ViewGroup,
         viewType: Int
     ): RecyclerView.ViewHolder {
         val inflater = LayoutInflater.from(parent.context)
         return ObjectiveViewHolder(ItemObjectiveAchieveBinding.inflate(inflater, parent, false))
     }

     override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
         val item = getItem(position)
         when (holder) {
             is ObjectiveViewHolder -> {
                 holder.bind(item, clickListener)
             }
         }
     }

     inner class ObjectiveViewHolder(
         private val binding: ItemObjectiveAchieveBinding,
     ): RecyclerView.ViewHolder(binding.root) {

         fun bind(item: ObjectiveWithKeyResults, clickListener: ItemClickListener) {
             binding.objective = item
             binding.clickListener = clickListener

             when (item.keyResults?.size) { //TODO(추후 여기에 보여줄 KeyResult 를 선택하는 항목도 넣을 수 있음)
                 null, 0 -> {
                     binding.layoutKeyList.visibility = View.GONE
                 }
                 1 -> {
                     binding.tvKeyResult1.text = item.keyResults[0].title
                     binding.tvKeyResult2.visibility = View.GONE
                 }
                 else -> {
                     binding.tvKeyResult1.text = item.keyResults[0].title
                     binding.tvKeyResult2.text = item.keyResults[1].title
                 }
             }
             binding.deleteItemView.setOnClickListener {
                 Log.d("TEST_swipe_delete","${item.objective.id} click")
                 objectiveViewModel.deleteAchieveObjective(item.objective.id)
             }
         }
     }

     class ItemClickListener(val clickListener: (String) -> Unit) {
         fun onClick(objective: ObjectiveWithKeyResults) = clickListener(objective.objective.id)
     }
 }