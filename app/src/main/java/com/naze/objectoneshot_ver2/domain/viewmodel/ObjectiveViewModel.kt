package com.naze.objectoneshot_ver2.domain.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naze.objectoneshot_ver2.data.local.model.KeyResult
import com.naze.objectoneshot_ver2.data.local.model.Objective
import com.naze.objectoneshot_ver2.data.local.model.Task
import com.naze.objectoneshot_ver2.domain.repository.ObjectiveRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ObjectiveViewModel @Inject constructor(
    private val objectiveRepository: ObjectiveRepository
): ViewModel() {
    private val _objectiveList = MutableLiveData<List<Objective>>() //Objective 리스트
    val objectiveList: LiveData<List<Objective>> get() = _objectiveList

    private val _objective = MutableLiveData<Objective>()
    val objective: LiveData<Objective> get() = _objective

    private val _keyResultList = MutableLiveData<List<KeyResult>>()
    val keyResultList: LiveData<List<KeyResult>> get() = _keyResultList

    private val _keyResult = MutableLiveData<KeyResult>()
    val keyResult: LiveData<KeyResult> get() = _keyResult //KeyList 추가할 때 쓰는 아이템

    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> get() = _taskList

    private val _task = MutableLiveData<Task>()
    val task: LiveData<Task> get() = _task

    fun insertObjective() {
        viewModelScope.launch(Dispatchers.IO) {
            val id = _objective.value?.let { objectiveRepository.insert(it) }
            Log.d("TEST_ObjectiveViewModel","insertObjective $id")
        }
    }

    fun setObjectiveDateRange(startDate: Long, endDate: Long) {
        _objective.value = _objective.value?.copy(startDate = startDate, endDate = endDate)
        Log.d("TEST_ObjectiveViewModel","${_objective.value}")
    } //TextView 이기 때문에 입력값이 없어서 직접 메소드로 입력

    fun setObjectiveProgress() {
        //KeyResult 데이터를 가지고 계산
    } //KeyResult 데이터를 가지고 계산해야 하기에 처리

    fun initObjectiveData() {
        _objective.value = Objective(
            title = "",
            startDate = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis,
            endDate = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis,
            progress = 0.0,
            complete = false
        )
        Log.d("TEST_ObjectiveViewModel","Init ObjectiveData ${objective.value}")
    } //ObjectiveData Add 위해 초기값 설정

    fun initKeyResultData() {
        _keyResult.value = KeyResult(
            title = "",
            progress = 0.0,
            objective_id = _objective.value!!.id
        )
    }//objective_id를 가지고 새로운 KeyResult 를 추가

    fun addKeyResultList() {
        val currentList = _keyResultList.value ?: mutableListOf()
        val newList = currentList.toMutableList().apply {
            _keyResult.value?.let { add(it) }
        }
        _keyResultList.value = newList
        Log.d("TEST_ObjectiveViewModel","KeyResultList : ${_keyResultList.value}")

        //TODO(List에 추가된 경우 -> Objective 의 Progress가 변한다)
    } //init 한 KeyResult 를 가지고 List 에 추가

    fun modifyKeyResultList() {

    }

    fun initTaskData() {

    }

    fun addOrUpdateTaskData(task: Task) {
        val currentList = _taskList.value ?: mutableListOf()
        val index = currentList.indexOfFirst { it.id == task.id }
        if (index == -1) {
            _taskList.value = currentList + task
        } else {
            val newList = currentList.toMutableList().apply {
                set(index, task)
            }
            _taskList.value = newList
        }
        Log.d("TEST_ObjectiveViewModel","taskList : ${_taskList.value}")
    }
}