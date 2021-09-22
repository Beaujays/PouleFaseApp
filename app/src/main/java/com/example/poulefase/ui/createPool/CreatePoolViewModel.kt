package com.example.poulefase.ui.createPool

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.poulefase.data.objects.PoolTeams
import com.example.poulefase.data.objects.Pools
import com.example.poulefase.data.objects.Teams
import com.example.poulefase.data.repositories.PoolTeamsRepository
import com.example.poulefase.data.repositories.PoolsRepository
import com.example.poulefase.data.repositories.TeamsRepository
import kotlinx.coroutines.launch

class CreatePoolViewModel(private val repository: PoolsRepository, teamsRepository: TeamsRepository, private val poolTeamsRepository: PoolTeamsRepository) : ViewModel() {
    // Get insert from repository
    fun insert(pools: Pools) = viewModelScope.launch {
        repository.insert(pools)
    }

    val allTeams: LiveData<List<Teams>> = teamsRepository.getAll()

    fun insertPoolTeams(poolTeams: PoolTeams) = viewModelScope.launch {
        poolTeamsRepository.insert(poolTeams)
    }

}

class CreatePoolViewModelFactory(private val repository: PoolsRepository, private val teamsRepository: TeamsRepository, private val poolTeamsRepository: PoolTeamsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CreatePoolViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreatePoolViewModel(repository,teamsRepository, poolTeamsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}