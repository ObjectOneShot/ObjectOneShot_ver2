package com.objectiveoneshot.objectiveoneshot.domain.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.objectiveoneshot.objectiveoneshot.data.local.model.*
import com.objectiveoneshot.objectiveoneshot.domain.repository.ObjectiveRepository
import com.objectiveoneshot.objectiveoneshot.domain.type.KeyResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val objectiveRepository: ObjectiveRepository
) : ViewModel() {
    private val _objectiveListWithKeyResults = MutableLiveData<List<ObjectiveWithKeyResults>>()
    val objectiveListWithKeyResults : LiveData<List<ObjectiveWithKeyResults>> get() = _objectiveListWithKeyResults

    private val _objectiveData = MutableLiveData<Objective>()
    val objectiveData: LiveData<Objective> get() = _objectiveData
    private val _keyResultWithTasks = MutableLiveData<List<KeyResultWithTasks>>()
    val keyResultWithTasks : LiveData<List<KeyResultWithTasks>> get() = _keyResultWithTasks

    private val _keyResultState = MutableLiveData<KeyResultState>()
    val keyResultState : LiveData<KeyResultState> get() = _keyResultState

    /** 데이터 insert */

    /** 데이터 select / get */

    /** 데이터 초기화 */
    fun getObjectiveData(id: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _objectiveData.value = objectiveRepository.getObjectiveById(id)
            _keyResultWithTasks.value = objectiveRepository.getKeyResultWithTasksById(id)
        }
    } //Add/Modify Objective 시 데이터 가져오기

    fun setObjectiveDateRange(startDate: Long, endDate: Long) {
        _objectiveData.value = _objectiveData.value?.copy(startDate = startDate, endDate = endDate)
    } //Objective Date 범위

    fun setKeyResultState(keyResultState: KeyResultState) {
        _keyResultState.value = keyResultState
    }

    fun addKeyResult() {
        val currentList = _keyResultWithTasks.value ?: mutableListOf()
        val newKeyResult = KeyResultWithTasks(
            keyResult = KeyResult(title = "", progress = 0.0, objective_id = _objectiveData.value?.id?:""),
            tasks = emptyList()
        )
        val newItem = newKeyResult.tasks.toMutableList().apply {
            add(Task(content = "", key_result_id = newKeyResult.keyResult.id))
        }

        val newList = currentList.toMutableList().apply {
            add(newKeyResult.copy(tasks = newItem))
        }
        _keyResultWithTasks.value = newList
        Log.d("TT_keyResult","${_keyResultWithTasks.value}\n$newItem")
        setObjectiveProgress()
    }

    /**
     * @param id KeyResult_id
     */
    fun addTask(keyResultId: String) {
        val currentList = _keyResultWithTasks.value ?: mutableListOf()
        val newList = currentList.map { keyResultWithTasks ->
            if (keyResultWithTasks.keyResult.id == keyResultId) {
                val newTasks = keyResultWithTasks.tasks.toMutableList().apply {
                    add(Task(content = "", key_result_id = keyResultId))
                }
                keyResultWithTasks.copy(tasks = newTasks)
            } else {
                keyResultWithTasks
            }
        }
        Log.d("TT_tasks","${_keyResultWithTasks.value}")
        _keyResultWithTasks.value = newList

        setKeyResultProgress(keyResultId)
    } //이거 Task 추가하는 방식 개편 가능할 듯

    fun deleteKeyResult(keyResultId: String) {
        val newList = _keyResultWithTasks.value.orEmpty().filter { it.keyResult.id != keyResultId }
        _keyResultWithTasks.value = newList

        setObjectiveProgress()
    }

    fun deleteTask(keyResultId: String, taskId: String) {
        val newList = _keyResultWithTasks.value.orEmpty().map { keyResultWithTasks ->
            if (keyResultWithTasks.keyResult.id == keyResultId) {
                val newTasks = keyResultWithTasks.tasks.filter { it.id != taskId }
                keyResultWithTasks.copy(tasks = newTasks)
            } else {
                keyResultWithTasks
            }
        }
        _keyResultWithTasks.value = newList

        setKeyResultProgress(keyResultId)
    }

    fun setKeyResultProgress(keyResultId: String) {
        val tasks = _keyResultWithTasks.value?.first { it.keyResult.id == keyResultId }?.tasks
        val progress = if (!tasks.isNullOrEmpty()) {
            100 * tasks.filter { it.check }.size / tasks.size //100 곱해주는 이유는 % 값이라서
        } else {
            0
        }.toDouble()
        val newList = _keyResultWithTasks.value.orEmpty().map { keyResultWithTasks ->
            if (keyResultWithTasks.keyResult.id == keyResultId) {
                keyResultWithTasks.copy(keyResult = keyResultWithTasks.keyResult.copy(progress = progress))
            } else {
                keyResultWithTasks
            }
        }
        _keyResultWithTasks.value = newList

        setObjectiveProgress()
    }

    private fun setObjectiveProgress() {
        val keyResults = _keyResultWithTasks.value
        val progress = if (!keyResults.isNullOrEmpty()) {
            100 * keyResults.filter { it.keyResult.progress >= 100 }.size / keyResults.size
        } else {
            0
        }.toDouble()
        val objective = _objectiveData.value
        _objectiveData.value = objective?.copy(progress = progress)
        Log.d("TT_progress","$progress")
    } //Objective 의 progress 계산. keyResult progress 가 100 인 개수

    fun checkIsEmpty() { //비었으면 true, 안비었으면 false
        val test1 =
        _keyResultWithTasks.value?.any { it.keyResult.title.isEmpty() }
        val test2 =
        _keyResultWithTasks.value?.any {
            it.tasks.isEmpty()
        }
        val test3 =
        _objectiveData.value?.title?.isEmpty()

        Log.d("TT_test","$test1\n$test2\n$test3")
        Log.d("TT_test","${_keyResultWithTasks.value}")
    }

    suspend fun checkIsChange(id: String): Boolean { //변하면 true, 안변하면 false
        val objectiveBefore = objectiveRepository.getObjectiveById(id)
        val keyResultWithTasksBefore = objectiveRepository.getKeyResultWithTasksById(id)
        return !(objectiveBefore == _objectiveData.value
                && keyResultWithTasksBefore == _keyResultWithTasks)
    }

    suspend fun checkAchieveComplete(): Boolean {
        return false
    }

    suspend fun checkAchieveUnComplete(): Boolean {
        return false
    }
}

