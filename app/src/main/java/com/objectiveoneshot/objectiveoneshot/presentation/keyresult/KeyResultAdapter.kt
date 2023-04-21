package com.objectiveoneshot.objectiveoneshot.presentation.keyresult

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.objectiveoneshot.objectiveoneshot.data.local.model.KeyResult
import com.objectiveoneshot.objectiveoneshot.databinding.ItemKeyResultBinding
import com.objectiveoneshot.objectiveoneshot.domain.viewmodel.ObjectiveViewModel
import com.objectiveoneshot.objectiveoneshot.presentation.task.TaskListAdapter
import com.objectiveoneshot.objectiveoneshot.util.ItemDiffCallback


class KeyResultAdapter(
    private val objectiveViewModel: ObjectiveViewModel
): ListAdapter<KeyResult, RecyclerView.ViewHolder>(
    ItemDiffCallback<KeyResult>(
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.id == new.id}
    )
) {
    inner class KeyViewHolder(
        private val binding: ItemKeyResultBinding
    ): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ClickableViewAccessibility")
        fun bind(keyResult: KeyResult) {
            binding.keyResult = keyResult
            binding.executePendingBindings()
            Log.d("TEST_KeyResultAdapter","KeyResult Id : ${keyResult.id}")
            val taskListAdapter = TaskListAdapter(keyResult.id, objectiveViewModel)

            binding.rvTaskList.apply {
                adapter = taskListAdapter
                layoutManager = LinearLayoutManager(context)
                taskListAdapter.submitList(objectiveViewModel.getTaskList(keyResult.id))
                //objectiveViewModel 에서 해당 keyResult에 해당하는 TaskList 가져오기
            }
            binding.btnExpand.setOnClickListener {
                if (binding.rvTaskList.visibility == View.GONE) { //안 보일 때 보이게 하기
                    binding.rvTaskList.visibility = View.VISIBLE
                    binding.btnExpand.rotation = 180f
                } else {
                    binding.rvTaskList.visibility = View.GONE
                    binding.btnExpand.rotation = 360f
                }
            }
            binding.swipeLayout.setOnTouchListener { v, event ->
                if (binding.rvTaskList.visibility == View.VISIBLE) {
                    return@setOnTouchListener true
                } else {
                    false
                }
            }
            binding.deleteItemView.setOnClickListener {
                if (!binding.swipeLayout.isClosed) {
                    objectiveViewModel.deleteKeyResult(keyResult.id)
                }
            }
            binding.etKeyName.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    objectiveViewModel.modifyKeyResultData(keyResult)
                    binding.etKeyName.clearFocus()
                    return@setOnEditorActionListener true
                }
                false
            }
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