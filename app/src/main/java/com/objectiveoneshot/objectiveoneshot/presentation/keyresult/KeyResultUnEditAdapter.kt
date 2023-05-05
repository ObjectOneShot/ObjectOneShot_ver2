package com.objectiveoneshot.objectiveoneshot.presentation.keyresult

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.objectiveoneshot.objectiveoneshot.data.local.model.KeyResultWithTasks
import com.objectiveoneshot.objectiveoneshot.databinding.ItemKeyResultExpandUneditBinding
import com.objectiveoneshot.objectiveoneshot.databinding.ItemKeyResultUneditBinding
import com.objectiveoneshot.objectiveoneshot.domain.viewmodel.AppViewModel
import com.objectiveoneshot.objectiveoneshot.presentation.task.TaskListAdapter
import com.objectiveoneshot.objectiveoneshot.util.ItemDiffCallback


class KeyResultUnEditAdapter(
    private val viewModel: AppViewModel
): ListAdapter<KeyResultWithTasks, RecyclerView.ViewHolder>(
    ItemDiffCallback<KeyResultWithTasks>(
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.keyResult.id == new.keyResult.id}
    )
) {
    companion object {
        const val TYPE_EXPAND = 0
        const val TYPE_NORMAL = 1
    }
    inner class KeyViewHolder(
        private val binding: ItemKeyResultUneditBinding
    ): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ClickableViewAccessibility")
        fun bind(keyResult: KeyResultWithTasks) {
            binding.keyResult = keyResult
            binding.executePendingBindings()

            binding.btnExpand.setOnClickListener {
                getItem(adapterPosition).keyResult.isExpand = true
                notifyItemChanged(adapterPosition)
            }
            binding.etKeyName.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.etKeyName.clearFocus()
                    return@setOnEditorActionListener true
                }
                false
            }
        }
    }

    inner class KeyViewExpandHolder(
        private val binding: ItemKeyResultExpandUneditBinding
    ): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ClickableViewAccessibility")
        fun bind(keyResult: KeyResultWithTasks) {
            binding.keyResult = keyResult
            binding.executePendingBindings()
            val taskListAdapter = TaskListAdapter(keyResult.keyResult.id, viewModel)

            binding.rvTaskList.apply {
                adapter = taskListAdapter
                layoutManager = LinearLayoutManager(context)

                try {
                    taskListAdapter.submitList(viewModel.keyResultWithTasks.value?.first { (keyResult.keyResult.id) == it.keyResult.id }?.tasks)
                } catch (e: NoSuchElementException){
                    e.printStackTrace()
                }
            }
            binding.btnExpand.setOnClickListener {
                getItem(adapterPosition).keyResult.isExpand = false
                notifyItemChanged(adapterPosition)
            }
            binding.etKeyName.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.etKeyName.clearFocus()
                    return@setOnEditorActionListener true
                }
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_NORMAL -> {
                val inflater = LayoutInflater.from(parent.context)
                KeyViewHolder(ItemKeyResultUneditBinding.inflate(inflater, parent, false))
            }
            TYPE_EXPAND -> {
                val inflater = LayoutInflater.from(parent.context)
                KeyViewExpandHolder(ItemKeyResultExpandUneditBinding.inflate(inflater, parent, false))
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val keyResult = getItem(position)
        when (holder.itemViewType) {
            TYPE_NORMAL -> {
                (holder as KeyViewHolder).bind(keyResult)
            }
            TYPE_EXPAND -> {
                (holder as KeyViewExpandHolder).bind(keyResult)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).keyResult.isExpand) TYPE_EXPAND else TYPE_NORMAL
    }
}
