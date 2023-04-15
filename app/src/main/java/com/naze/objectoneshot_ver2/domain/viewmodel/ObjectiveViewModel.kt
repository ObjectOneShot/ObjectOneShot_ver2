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
            val list = objectiveRepository.getObjectiveWithKeyResultWithTask()
            Log.d("TEST_ObjectiveViewModel","getObjectiveWithKeyResultWithTask\n$list")
        }
    }

    fun getObjective() {

    }

    fun setObjectiveList() {

    }
}