package com.example.poulefase.ui.listOfGames

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.poulefase.data.objects.Games
import com.example.poulefase.data.objects.Pools
import com.example.poulefase.data.repositories.GamesRepository
import com.example.poulefase.data.repositories.PoolsRepository

class ListOfGamesViewModel(repository: GamesRepository) : ViewModel()
{
    val allGames: LiveData<List<Games>> = repository.getAll()
}

class ListOfGamesViewModelFactory(
    private val repository: GamesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        check(modelClass.isAssignableFrom(ListOfGamesViewModel::class.java))

        @Suppress("UNCHECKED_CAST")
        return ListOfGamesViewModel(repository) as T
    }
}