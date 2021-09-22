package com.example.poulefase.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.poulefase.data.objects.Games
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QueryDocumentSnapshot

class GamesRepository(private  val gamesCollectionReference: CollectionReference) {

    private val defaultGames = Games(
        "", "One moment please", "","",
        "", "", "", "", "", ""
    )

    private val all: MutableLiveData<List<Games>> =
        MutableLiveData(listOf(defaultGames))


    // Post object on FireStore
    fun insert(games: Games) {
        gamesCollectionReference.document().set(
            games.toGamesData()
        ).addOnSuccessListener { getAll() }
    }

    // Get all from FireStore
    fun getAll(): LiveData<List<Games>> {
        gamesCollectionReference.get()
            .addOnSuccessListener { documents ->
                documents
                    .map(QueryDocumentSnapshot::toGames)
                    .let(all::postValue)
            }
        return all
    }

}