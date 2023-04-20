package com.naze.objectoneshot_ver2.presentation.keyresult

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.utils.widget.ImageFilterButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.naze.objectoneshot_ver2.R
import com.naze.objectoneshot_ver2.data.local.model.KeyResult
import com.naze.objectoneshot_ver2.databinding.ItemKeyResultBinding
import com.naze.objectoneshot_ver2.domain.viewmodel.ObjectiveViewModel
import com.naze.objectoneshot_ver2.presentation.task.TaskListAdapter
import com.naze.objectoneshot_ver2.util.ItemDiffCallback


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
                    binding.swipeLayout.isEnabledSwipe = false
                } else {
                    binding.rvTaskList.visibility = View.GONE
                    binding.btnExpand.rotation = 360f
                    binding.swipeLayout.isEnabledSwipe = true
                }
            }

            binding.deleteItemView.setOnClickListener {
                if (!binding.swipeLayout.isClosed) {
                    val dialog: Dialog = Dialog(itemView.context)
                    dialog.setContentView(R.layout.dialog_delete)
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.show()
                    dialog.findViewById<ImageFilterButton>(R.id.btn_delete_dialog)
                        .setOnClickListener {
                            dialog.dismiss()
                            objectiveViewModel.deleteKeyResult(keyResult.id)
                            Log.d("TEST_swipe_delete", "${keyResult.id} click")
                        }
                    dialog.findViewById<ImageFilterButton>(R.id.btn_cancel_dialog)
                        .setOnClickListener {
                            dialog.dismiss()
                        }
                }
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
