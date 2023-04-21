package com.naze.objectoneshot_ver2.presentation.task

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.naze.objectoneshot_ver2.data.local.model.Task
import com.naze.objectoneshot_ver2.databinding.ItemTaskBinding
import com.naze.objectoneshot_ver2.databinding.ItemTaskUneditBinding
import com.naze.objectoneshot_ver2.domain.viewmodel.ObjectiveViewModel
import com.naze.objectoneshot_ver2.util.ItemDiffCallback

class TaskListUnEditAdapter(
    private val keyResultId: String,
): ListAdapter<Task, RecyclerView.ViewHolder>(
    ItemDiffCallback<Task> (
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.id == new.id}
    )
) {
    inner class TaskAddViewHolder(
        private val binding: ItemTaskUneditBinding,
    ): RecyclerView.ViewHolder(binding.root) {
        init {
        }

        fun bind(item: Task) {
            binding.task = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TaskAddViewHolder(ItemTaskUneditBinding.inflate(inflater,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TaskAddViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }

    override fun submitList(list: List<Task>?) {
        if (list.isNullOrEmpty()) {
            super.submitList(listOf(Task("", key_result_id = keyResultId)))
        } else {
            super.submitList(list)
        }
    }
}