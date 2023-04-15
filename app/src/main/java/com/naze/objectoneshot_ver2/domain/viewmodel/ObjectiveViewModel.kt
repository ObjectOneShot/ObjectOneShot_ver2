package com.naze.objectoneshot_ver2.domain.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naze.objectoneshot_ver2.data.local.model.Objective
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

    fun getObjectiveList() {
        viewModelScope.launch(Dispatchers.IO){
        }
    }
    fun insertObjectiveList() {

    }

    fun insertObjective() {
        viewModelScope.launch(Dispatchers.IO) {
            val id = _objective.value?.let { objectiveRepository.insert(it) }
            Log.d("TEST_ObjectiveViewModel","insertObjective $id")
        }
    }

    fun setObjectiveDateRange(startDate: Long, endDate: Long) {
        _objective.value?.copy(startDate = startDate, endDate = endDate)
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

    fun setObjectiveDataById(id: Int) {

    }
}