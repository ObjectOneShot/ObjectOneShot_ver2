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
import com.objectiveoneshot.objectiveoneshot.data.local.model.KeyResultWithTasks
import com.objectiveoneshot.objectiveoneshot.databinding.TestItemKeyResultBinding
import com.objectiveoneshot.objectiveoneshot.domain.viewmodel.AppViewModel
import com.objectiveoneshot.objectiveoneshot.presentation.task.TestTaskListAdapter
import com.objectiveoneshot.objectiveoneshot.util.ItemDiffCallback


class TestKeyResultAdapter(
    private val viewModel: AppViewModel
): ListAdapter<KeyResultWithTasks, RecyclerView.ViewHolder>(
    ItemDiffCallback<KeyResultWithTasks>(
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.keyResult.id == new.keyResult.id}
    )
) {
    inner class KeyViewHolder(
        private val binding: TestItemKeyResultBinding
    ): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ClickableViewAccessibility")
        fun bind(keyResult: KeyResultWithTasks) {
            binding.keyResult = keyResult
            binding.executePendingBindings()
            Log.d("TEST_KeyResultAdapter","KeyResult Id : ${keyResult.keyResult.id}")
            Log.d("TEST_Test_KeyResultAdapter","filter : ${viewModel.keyResultWithTasks.value?.filter { (keyResult.keyResult.id) == it.keyResult.id }?.first()?.tasks}")
            val taskListAdapter = TestTaskListAdapter(keyResult.keyResult.id, viewModel)

            binding.rvTaskList.apply {
                adapter = taskListAdapter
                layoutManager = LinearLayoutManager(context)
                taskListAdapter.submitList(viewModel.keyResultWithTasks.value?.first { (keyResult.keyResult.id) == it.keyResult.id }?.tasks) //NoSuchElementException 필요
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
                   viewModel.deleteKeyResult(keyResult.keyResult.id)
                }
            }
            binding.etKeyName.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //objectiveViewModel.modifyKeyResultData(keyResult)
                    binding.etKeyName.clearFocus()
                    Log.d("TEST_Test_KeyResultAdapter","clearCursor : ${viewModel.keyResultWithTasks.value?.filter { (keyResult.keyResult.id) == it.keyResult.id }}")
                    return@setOnEditorActionListener true
                }
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return KeyViewHolder(TestItemKeyResultBinding.inflate(inflater, parent, false))
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
