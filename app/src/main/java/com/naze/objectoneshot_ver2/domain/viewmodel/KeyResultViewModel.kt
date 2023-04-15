package com.naze.objectoneshot_ver2.domain.viewmodel

import androidx.lifecycle.ViewModel
import com.naze.objectoneshot_ver2.domain.repository.KeyResultRepository
import com.naze.objectoneshot_ver2.domain.repository.ObjectiveRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KeyResultViewModel @Inject constructor(
    private val keyResultRepository: KeyResultRepository
): ViewModel() {
}