package com.naze.objectoneshot_ver2.domain.viewmodel

import androidx.lifecycle.ViewModel
import com.naze.objectoneshot_ver2.domain.repository.ObjectiveRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ObjectiveViewModel @Inject constructor(
    private val objectiveRepository: ObjectiveRepository
): ViewModel() {
}