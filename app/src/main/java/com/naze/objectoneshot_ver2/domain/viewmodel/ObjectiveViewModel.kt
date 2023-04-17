package com.naze.objectoneshot_ver2.domain.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naze.objectoneshot_ver2.data.local.model.KeyResult
import com.naze.objectoneshot_ver2.data.local.model.Objective
import com.naze.objectoneshot_ver2.data.local.model.ObjectiveWithKeyResults
import com.naze.objectoneshot_ver2.data.local.model.Task
import com.naze.objectoneshot_ver2.domain.repository.ObjectiveRepository
import com.naze.objectoneshot_ver2.domain.type.KeyResultState
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

    private val _objectiveListWithKeyResults = MutableLiveData<List<ObjectiveWithKeyResults>>()
    val objectiveListWithKeyResults : LiveData<List<ObjectiveWithKeyResults>> get() = _objectiveListWithKeyResults

    private val _objective = MutableLiveData<Objective>()
    val objective: LiveData<Objective> get() = _objective

    private val _keyResultList = MutableLiveData<List<KeyResult>?>()
    val keyResultList: LiveData<List<KeyResult>?> get() = _keyResultList

    private val _keyResult = MutableLiveData<KeyResult>()
    val keyResult: LiveData<KeyResult> get() = _keyResult //KeyList 추가할 때 쓰는 아이템

    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> get() = _taskList

    private val _task = MutableLiveData<Task>()
    val task: LiveData<Task> get() = _task

    private val _keyResultState = MutableLiveData<KeyResultState>()
    val keyResultState: LiveData<KeyResultState> get() = _keyResultState

    /***
     * 데이터 Insert
     */
    //insert
    fun insertObjective() {
        viewModelScope.launch(Dispatchers.IO) {
            _objective.value?.let { objectiveRepository.insertObjective(it) }
            Log.d("TEST_Insert","insertObjective ${_objective.value?.id}")
            insertKeyResult()
        }
    }

    private fun insertKeyResult() {
        Log.d("TEST_Insert","objective id = ${_keyResultList.value?.get(0)?.objective_id}")
        Log.d("TEST_Insert","key_result id = ${_keyResultList.value?.get(0)?.id}")
        viewModelScope.launch(Dispatchers.IO) {
            _keyResultList.value?.let { objectiveRepository.insertKeyResult(it)}
            insertTask()
        }
    }

    private fun insertTask() {
        viewModelScope.launch(Dispatchers.IO) {
            _taskList.value?.let {
                for(i in it) {
                    Log.d("TEST_insert", "key_result_id = ${i.key_result_id}")
                    objectiveRepository.insertTask(i)
                }
            }
        }
    }

    /***
     * 데이터 SELECT (GET)
     */
    fun getObjectiveList() {
        viewModelScope.launch(Dispatchers.Main) {
            _objectiveListWithKeyResults.value = objectiveRepository.getObjective()
        }
    } //ObjectiveList 가져오기 추후 변경 예정

    fun getObjectiveAchieveList() {
        viewModelScope.launch(Dispatchers.Main) {
            _objectiveListWithKeyResults.value = objectiveRepository.getAchieveObjective()
        }
    } //ObjectiveAchieveList 가져오기 추후 변경 예정



    /** 데이터 초기화 - Add Fragment
     *  데이터 신규 생성
     */

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
            complete = false,
        )
        Log.d("TEST_initObjective","id = ${_objective.value?.id}")
        initKeyResultList()
    } //ObjectiveData Add 위해 초기값 설정

    fun initKeyResultData() {
        _keyResult.value = KeyResult(
            title = "",
            progress = 0.0,
            objective_id = _objective.value!!.id,
        )
        Log.d("TEST_initKeyResult","objective id = ${_objective.value?.id}")
        Log.d("TEST_initKeyResult","keyResult id = ${_keyResult.value?.id}")
    }//objective_id를 가지고 새로운 KeyResult 를 추가

    private fun initKeyResultList() {
        _keyResultList.value = null
    }

    /**데이터 초기화
     * Modify Fragment - 수정 위한 데이터
     */
    fun getObjectiveData(id : String) {
        viewModelScope.launch(Dispatchers.Main) {
            _objective.value = objectiveRepository.getObjectiveById(id)
            val value = objectiveRepository.getKeyResultWithTasksById(id)
            Log.d("TEST_getAllData","$value")
        }
    }

    /** 데이터 초기화 - 기타
     *  KeyResult State 값
     */
    fun initKeyResultState() {
        _keyResultState.value = KeyResultState.BEFORE_PROGRESS //시작할 땐 _keyResultState를 BEFORE로 초기화
    }




    /***
     * 하단 미분류
     */

    /**
     * view 에서 button 으로 state 변경할 때 사용
     */
    fun setKeyResultState(keyResultState: KeyResultState) {
        _keyResultState.value = keyResultState
    }

    /**
     * @param progress KeyResult의 Progress 값
     * KeyResult Progress 값을 통하여 KeyResultState 변경
     */
    private fun setKeyResultStateByProgress(progress: Double) {
        _keyResultState.value = when (progress) {
            0.0 -> {
                KeyResultState.BEFORE_PROGRESS
            }
            in 0.1..99.9 -> {
                KeyResultState.ON_PROGRESS
            }
            else -> {
                KeyResultState.COMPLETE
            }
        }
    }

    /**
     * @param startDate 시작일
     * @param endDate 종료일
     * Objective 의 Date Range 설정
     */
    fun setObjectiveDateRange(startDate: Long, endDate: Long) {
        _objective.value = _objective.value?.copy(startDate = startDate, endDate = endDate)
        Log.d("TEST_ObjectiveViewModel","${_objective.value}")
    } //TextView 이기 때문에 입력값이 없어서 직접 메소드로 입력

    /**
     * Objective Progress 설정
     */
    private fun setObjectiveProgress() {
        val list = _keyResultList.value ?: mutableListOf()
        var sum = 0.0
        for (i in list) {
            sum += i.progress
        }
        sum/list.size
        _objective.value = _objective.value?.copy(progress = if (list.isNotEmpty()) sum/list.size else 0.0)
    } //KeyResult 데이터를 가지고 계산해야 하기에 처리

    /**
     * init 한 KeyResult 를 가지고 List 에 추가
     */
    fun addKeyResultList() {
        val currentList = _keyResultList.value ?: mutableListOf()
        val newList = currentList.toMutableList().apply {
            _keyResult.value?.let { add(it) }
        }
        _keyResultList.value = newList
        Log.d("TEST_ObjectiveViewModel","KeyResultList : ${_keyResultList.value}")
        setKeyResultStateByProgress(_keyResult.value?.progress?:0.0)
        setObjectiveProgress()
        //TODO(List에 추가된 경우 -> Objective 의 Progress가 변한다)
    } //init 한 KeyResult 를 가지고 List 에 추가

    /**
     * Task 를 Add 나 Update 할 때 사용
     * viewModel 내부 TaskList
     */
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
        Log.d("TEST_ObjectiveViewModel","taskList Add or Update: ${_taskList.value}")
    }

    /**
     * Task 삭제
     */
    fun deleteTaskData(task: Task) {
        val currentList = _taskList.value ?: mutableListOf()
        val index = currentList.indexOfFirst { it.id == task.id }
        if (index != -1) {
            Log.d("TEST_ObjectiveViewModel","taskList Delete : ${currentList[index]}")
            _taskList.value = currentList.filterNot { it.id == task.id }
        }
        Log.d("TEST_ObjectiveViewModel","taskList Delete : ${_taskList.value}")
    }

    /**
     * @param id = key_result_id
     */
    fun changeKeyResultListProgress(id: String){
        Log.d("TEST_changeKey","id: $id")
        val list = _taskList.value ?: mutableListOf()
        val newList = list.filter { it.key_result_id == id }
        val progress = if (newList.isNotEmpty()) {
            var cnt = 0 //check 된 개수
            for (i in newList) {
                if (i.check) cnt ++
            }
            100 * cnt / newList.size
        } else { 0 }.toDouble()

        val currentList = _keyResultList.value ?: mutableListOf()
        val updatedList = currentList.map {
            if (it.id == id) {
                it.copy(progress = progress)
            } else {
                it
            }
        }
        _keyResultList.value = updatedList
        setKeyResultStateByProgress(progress)
        setObjectiveProgress()
    } //KeyResultList Progress 변경

    /**
     * @param id = key_result_id
     */
    fun changeKeyResultProgress(id: String) {
        val list = _taskList.value ?: mutableListOf()
        val newList = list.filter { it.key_result_id == id }
        val progress = if (newList.isNotEmpty()) {
            var cnt = 0 //check 된 개수
            for (i in newList) {
                if (i.check) cnt ++
            }
            100 * cnt / newList.size
        } else { 0 }.toDouble()
        _keyResult.value = _keyResult.value?.copy(progress = progress)
    } //KeyResult Progress (등록하기 전, state 안바꾸는게 맞을듯)

    /**
     * @param id KeyResultId
     * KeyResultId 로 해당하는 TaskList 가져오기
     */
    fun getTaskList(id: String): List<Task>? {
        return _taskList.value?.filter { it.key_result_id == id }
    }
}