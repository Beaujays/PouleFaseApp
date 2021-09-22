package com.example.poulefase.ui.detailPool

import androidx.lifecycle.*
import com.example.poulefase.data.objects.Games
import com.example.poulefase.data.objects.PoolTeams
import com.example.poulefase.data.objects.Pools
import com.example.poulefase.data.repositories.GamesRepository
import com.example.poulefase.data.repositories.PoolTeamsRepository
import com.example.poulefase.data.repositories.PoolsRepository
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class DetailPoolViewModel(
    private val repositoryPoolTeams: PoolTeamsRepository,
    repositoryPools: PoolsRepository,
    private val repositoryGames: GamesRepository,
    val id: String
) : ViewModel() {

    // Set value with mutable live data
    val pool: MutableLiveData<Pools> =
        repositoryPools.getById(id) as MutableLiveData<Pools>
    val poolTeams: LiveData<List<PoolTeams>> = repositoryPoolTeams.getAll()
    private val games: LiveData<List<Games>> = repositoryGames.getAll()

    val allPoolTeams: LiveData<List<PoolTeams>>
        get() = poolTeams
            .map { list ->
                (list)
                    .filter { poolTeams ->
                        poolTeams.PoolID == pool.value?.PoolID
                    }
                    .toList()
            }

    val allGamesByPoolId: LiveData<List<Games>>
        get() = games
            .map { list ->
                (list)
                    .filter { game ->
                        game.PoolID == pool.value?.PoolID
                    }
                    .toList()
            }

    // Get Team based on sequenceNb and poolID
    fun getTeamID(sequenceNb: String, poolID: String): LiveData<PoolTeams>? {
        return repositoryPoolTeams.getTeamId(sequenceNb, poolID)
    }

    // Insert games in FireStore
    fun insertGames(games: Games) = viewModelScope.launch {
        repositoryGames.insert(games)
    }

    // Insert games in FireStore
    fun updatePoolTeams(poolTeams: PoolTeams) = viewModelScope.launch {
        repositoryPoolTeams.update(poolTeams)
    }
}

class DetailPoolViewModelFactory(
    private val repositoryPoolTeams: PoolTeamsRepository,
    private val repositoryPools: PoolsRepository,
    private val repositoryGames: GamesRepository,
        private val poolID: String

) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        check(modelClass.isAssignableFrom(DetailPoolViewModel::class.java))

        @Suppress("UNCHECKED_CAST")
        return DetailPoolViewModel(repositoryPoolTeams, repositoryPools,repositoryGames, poolID) as T
    }
}