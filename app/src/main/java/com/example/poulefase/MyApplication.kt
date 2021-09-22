package com.example.poulefase

import androidx.multidex.MultiDexApplication
import com.example.poulefase.data.repositories.GamesRepository
import com.example.poulefase.data.repositories.PoolTeamsRepository
import com.example.poulefase.data.repositories.PoolsRepository
import com.example.poulefase.data.repositories.TeamsRepository
import com.google.firebase.firestore.FirebaseFirestore


class MyApplication : MultiDexApplication() {
    private val firestore by lazy { FirebaseFirestore.getInstance() }
    val gamesRepository by lazy { GamesRepository(firestore.collection("Games")) }
    val poolsRepository by lazy { PoolsRepository(firestore.collection("Pools")) }
    val poolTeamsRepository by lazy { PoolTeamsRepository(firestore.collection("PoolTeams")) }
    val teamsRepository by lazy { TeamsRepository(firestore.collection("Teams")) }
}