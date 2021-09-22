package com.example.poulefase.ui.listOfPools

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.poulefase.data.objects.Pools
import com.example.poulefase.data.repositories.PoolsRepository

class ListOfPoolsViewModel(repository: PoolsRepository) : ViewModel()
{
    val allPools: LiveData<List<Pools>> = repository.getAll()
}

class ListOfPoolsViewModelFactory(
    private val repository: PoolsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        check(modelClass.isAssignableFrom(ListOfPoolsViewModel::class.java))

        @Suppress("UNCHECKED_CAST")
        return ListOfPoolsViewModel(repository) as T
    }
}