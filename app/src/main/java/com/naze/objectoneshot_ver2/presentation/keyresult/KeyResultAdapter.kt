package com.naze.objectoneshot_ver2.presentation.keyresult

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.naze.objectoneshot_ver2.data.local.model.KeyResult
import com.naze.objectoneshot_ver2.databinding.ItemKeyResultBinding
import com.naze.objectoneshot_ver2.presentation.task.TaskListAdapter
import com.naze.objectoneshot_ver2.util.ItemDiffCallback


class KeyResultAdapter(
): ListAdapter<KeyResult, RecyclerView.ViewHolder>(
    ItemDiffCallback<KeyResult>(
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.id == new.id}
    )
){
    inner class KeyViewHolder(
        private val binding: ItemKeyResultBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(keyResult: KeyResult) {
            binding.keyResult = keyResult
            binding.executePendingBindings()
            Log.d("TEST_KeyResultAdapter","KeyResult $keyResult")
            val taskListAdapter = TaskListAdapter(keyResult.id)
            binding.rvTaskList.adapter = taskListAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return KeyViewHolder(ItemKeyResultBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val keyResult = getItem(position)
        when (holder) {
            is KeyViewHolder -> {
                holder.bind(keyResult)
            }
        }
    }
}
