package com.naze.objectoneshot_ver2.presentation.objective

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.naze.objectoneshot_ver2.data.local.model.Objective
import com.naze.objectoneshot_ver2.databinding.ItemObjectiveBinding
import com.naze.objectoneshot_ver2.util.ItemDiffCallback

class ObjectiveListAdapter(
    private val clickListener: ItemClickListener
): ListAdapter<Objective, RecyclerView.ViewHolder>(
    ItemDiffCallback<Objective>(
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.id == new.id}
    ))
 {
     override fun onCreateViewHolder(
         parent: ViewGroup,
         viewType: Int
     ): RecyclerView.ViewHolder {
         val inflater = LayoutInflater.from(parent.context)
         return ObjectiveViewHolder(ItemObjectiveBinding.inflate(inflater, parent, false))
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
         private val binding: ItemObjectiveBinding,
     ): RecyclerView.ViewHolder(binding.root) {

         fun bind(item: Objective, clickListener: ItemClickListener) {
             binding.objective = item
             binding.clickListener = clickListener
         }
     }

     class ItemClickListener(val clickListener: (String) -> Unit) {
         fun onClick(objective: Objective) = clickListener(objective.id)
     }
 }