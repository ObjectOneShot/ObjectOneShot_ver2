package com.naze.objectoneshot_ver2.presentation.keyresult

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.naze.objectoneshot_ver2.data.local.model.KeyResult
import com.naze.objectoneshot_ver2.databinding.ItemKeyResultBinding
import com.naze.objectoneshot_ver2.databinding.ItemKeyResultUneditBinding
import com.naze.objectoneshot_ver2.domain.viewmodel.ObjectiveViewModel
import com.naze.objectoneshot_ver2.presentation.task.TaskListUnEditAdapter
import com.naze.objectoneshot_ver2.util.ItemDiffCallback


class KeyResultUnEditAdapter(
    private val objectiveViewModel: ObjectiveViewModel
): ListAdapter<KeyResult, RecyclerView.ViewHolder>(
    ItemDiffCallback<KeyResult>(
        onContentsTheSame = {old, new -> old == new},
        onItemsTheSame = {old, new -> old.id == new.id}
    )
) {

    inner class KeyViewHolder(
        private val binding: ItemKeyResultUneditBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(keyResult: KeyResult) {
            binding.keyResult = keyResult
            binding.executePendingBindings()
            Log.d("TEST_KeyResultAdapter","KeyResult Id : ${keyResult.id}")
            val taskListAdapter = TaskListUnEditAdapter(keyResult.id)

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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return KeyViewHolder(ItemKeyResultUneditBinding.inflate(inflater, parent, false))
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