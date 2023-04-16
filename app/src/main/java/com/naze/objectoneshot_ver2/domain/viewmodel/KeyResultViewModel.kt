package com.naze.objectoneshot_ver2.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naze.objectoneshot_ver2.data.local.model.KeyResult
import com.naze.objectoneshot_ver2.data.local.model.Task
import com.naze.objectoneshot_ver2.domain.repository.KeyResultRepository
import com.naze.objectoneshot_ver2.domain.repository.ObjectiveRepository
import com.naze.objectoneshot_ver2.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeyResultViewModel @Inject constructor(
    private val keyResultRepository: KeyResultRepository,
): ViewModel() {
    private val _keyResult = MutableLiveData<List<KeyResult>>()
    val keyResult: LiveData<List<KeyResult>> get() = _keyResult

    private val _task = MutableLiveData<List<Task>>()
    val task: LiveData<List<Task>> get() = _task

    fun addKeyResult(title: String) {

    }

}