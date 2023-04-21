package com.naze.objectoneshot_ver2.presentation.task

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.naze.objectoneshot_ver2.data.local.model.Task
import com.naze.objectoneshot_ver2.databinding.ItemTaskBinding
import com.naze.objectoneshot_ver2.domain.viewmodel.ObjectiveViewModel
import com.naze.objectoneshot_ver2.util.ItemDiffCallback
import com.naze.objectoneshot_ver2.util.showKeyboard

class TaskListAdapter(
    private val keyResultId: String,
    private val objectiveViewModel: ObjectiveViewModel
): ListAdapter<Task, RecyclerView.ViewHolder>(
    ItemDiffCallback<Task> (
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.id == new.id}
    )
) {
    inner class TaskAddViewHolder(
        private val binding: ItemTaskBinding,
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnDeleteTask.visibility = View.GONE

            binding.btnAddTask.setOnClickListener {
                if (binding.etTaskName.text.toString().isNotEmpty()) {
                    binding.btnAddTask.visibility = View.GONE
                    addItem()
                }
            }
            binding.btnDeleteTask.setOnClickListener {
                binding.etTaskName.text.clear()
            }
        }

        fun bind(item: Task) {
            binding.task = item
            Log.d("TEST_TaskListAdapter", "Key_result_id: ${item.key_result_id}")

            if (adapterPosition == itemCount - 1) {
                binding.btnAddTask.visibility = View.VISIBLE
            } else {
                binding.btnAddTask.visibility = View.GONE
            }
            binding.cbTaskComplete.setOnClickListener {
                if (binding.etTaskName.text.isNotEmpty()) {
                    addOrUpdateTaskList(item)
                    objectiveViewModel.changeKeyResultProgress(keyResultId)
                } else {
                    binding.cbTaskComplete.isChecked = !binding.cbTaskComplete.isChecked
                }
            }

            binding.etTaskName.setOnFocusChangeListener { v, hasFocus ->
                val text = binding.etTaskName.text.toString()
                if (!hasFocus) {//focus 가 해제 될 때
                    if (text.isNotEmpty()) {
                        addOrUpdateTaskList(item)
                        objectiveViewModel.changeKeyResultListProgress(keyResultId)
                        if (adapterPosition == itemCount - 1) {
                            binding.btnAddTask.visibility = View.VISIBLE
                        }
                    } else {
                        deleteItem()
                    }
                    binding.btnDeleteTask.visibility = View.GONE
                } else { //focus가 들어올 때
                    if (binding.btnAddTask.visibility == View.VISIBLE) binding.btnAddTask.visibility = View.GONE
                    binding.btnDeleteTask.visibility = View.VISIBLE
                }
            }
            binding.etTaskName.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.etTaskName.clearFocus()
                    return@setOnEditorActionListener true
                } else {
                    return@setOnEditorActionListener false
                }
            }


        }
        private fun addItem() {
            if (itemCount < 5) {
                val newTask = Task("",false,keyResultId)
                submitList(currentList.toMutableList().apply { add(newTask) })
            }
        }
        private fun deleteItem() {
            deleteTaskList(currentList[adapterPosition])
            submitList(currentList.toMutableList().apply { removeAt(adapterPosition) })
            objectiveViewModel.changeKeyResultListProgress(keyResultId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TaskAddViewHolder(ItemTaskBinding.inflate(inflater,parent,false))
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

    private fun addOrUpdateTaskList(item: Task) {
        objectiveViewModel.addOrUpdateTaskData(Task(
            item.content,
            item.check,
            item.key_result_id,
            item.id
        ))
    }

    private fun deleteTaskList(item: Task) {
        objectiveViewModel.deleteTaskData(item)
    }
}