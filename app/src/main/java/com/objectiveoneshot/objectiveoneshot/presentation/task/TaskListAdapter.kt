package com.objectiveoneshot.objectiveoneshot.presentation.task

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.objectiveoneshot.objectiveoneshot.R
import com.objectiveoneshot.objectiveoneshot.data.local.model.Task
import com.objectiveoneshot.objectiveoneshot.databinding.ItemTaskBinding
import com.objectiveoneshot.objectiveoneshot.domain.type.ItemId
import com.objectiveoneshot.objectiveoneshot.domain.type.ItemType
import com.objectiveoneshot.objectiveoneshot.domain.viewmodel.AppViewModel
import com.objectiveoneshot.objectiveoneshot.util.ItemDiffCallback

class TaskListAdapter(
    private val keyResultId: String,
    private val viewModel: AppViewModel
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
        }

        fun bind(item: Task) {
            binding.task = item

            if (item.content.isEmpty()) {
                binding.etTaskName.requestFocus()
            }

            binding.btnDeleteTask.visibility = View.GONE

            binding.btnAddTask.setOnClickListener {
                if (binding.etTaskName.text.toString().isNotEmpty()) {
                    Firebase.analytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                        param(FirebaseAnalytics.Param.ITEM_ID, ItemId.BUTTON.toString())
                        param(FirebaseAnalytics.Param.ITEM_NAME, ItemType.ADD_TASK.toString())
                    }
                    addItem(item)
                }
            }

            binding.btnDeleteTask.setOnClickListener {
                Firebase.analytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                    param(FirebaseAnalytics.Param.ITEM_ID, ItemId.BUTTON.toString())
                    param(FirebaseAnalytics.Param.ITEM_NAME, ItemType.DELETE_TASK.toString())
                }
                deleteItem(item)
            }

            when (adapterPosition) {
                itemCount - 1 -> { //삭제 시에 마지막 아이템이면 Add Task 보여주기
                    binding.btnAddTask.visibility = View.VISIBLE
                }
                else -> {
                    binding.btnAddTask.visibility = View.GONE
                }
            }

            binding.etTaskName.setOnFocusChangeListener { v, hasFocus ->
                val text = binding.etTaskName.text.toString()
                if (hasFocus) {
                    binding.btnDeleteTask.visibility = View.VISIBLE
                    binding.btnAddTask.visibility = View.GONE
                } else {
                    if (adapterPosition == itemCount - 1) {
                        if (text.isEmpty()) {
                            deleteItem(item)
                        }
                    } else {
                        if (text.isEmpty()) {
                            if (itemCount == 1) {
                                val dialog = Dialog(itemView.context)
                                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                dialog.setContentView(R.layout.dialog_task_alert)
                                dialog.show()
                            } else {
                                deleteItem(item)
                            }
                        }
                    }
                }
            }

            binding.etTaskName.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.etTaskName.clearFocus()
                    addItem(item)
                    return@setOnEditorActionListener true
                } else {
                    return@setOnEditorActionListener false
                }
            }

            binding.cbTaskComplete.setOnClickListener {
                Firebase.analytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                    param(FirebaseAnalytics.Param.ITEM_ID, ItemId.ITEM.toString())
                    param(FirebaseAnalytics.Param.ITEM_NAME, ItemType.CHECK_TASK.toString())
                }
                if(binding.etTaskName.text.toString().isNotEmpty()) {
                    viewModel.setKeyResultProgress(item.key_result_id)
                } else {
                    binding.cbTaskComplete.isChecked = false
                }
            }
        }

        private fun deleteItem(item: Task) {
            viewModel.deleteTask(item.key_result_id, item.id)
        }

        private fun addItem(item: Task) {
            viewModel.addTask(item.key_result_id)
            binding.btnAddTask.visibility = View.GONE
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
            viewModel.addTask(keyResultId)
        } else {
            super.submitList(list)
        }
    }
}