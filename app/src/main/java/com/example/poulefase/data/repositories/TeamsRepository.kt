package com.example.poulefase.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.poulefase.data.objects.Teams
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QueryDocumentSnapshot

class TeamsRepository(private val teamsCollectionReference: CollectionReference) {
    private val defaultTeams = Teams("test","One moment please", "", "")

    private val all: MutableLiveData<List<Teams>> =
            MutableLiveData(listOf(defaultTeams))

    // Post object on FireStore
    fun insert(teams: Teams)
    {
        teamsCollectionReference.document().set(
                teams.toTeamsData()
        ).addOnSuccessListener { getAll() }
    }

    // Fetch all from FireStore
    fun getAll(): LiveData<List<Teams>>
    {
        teamsCollectionReference.get()
                .addOnSuccessListener { documents ->
                    documents
                            .map(QueryDocumentSnapshot::toTeams)
                            .let(all::postValue)
                }
        return all
    }

    // Fetch from FireStore
    fun getTeam(id: String): LiveData<Teams>?
    {
        teamsCollectionReference.get()
            .addOnSuccessListener { documents ->
                documents
                    .map(QueryDocumentSnapshot::toTeams)
                    .let(all::postValue)
            }

        all.value?.forEach { res ->
            if (res.TeamID == id) {
                return getById(res.TeamID)
            }
        }
        return null
    }

    // Get by ID
    private fun getById(id: String): LiveData<Teams> {
        val result = MutableLiveData(defaultTeams)
        teamsCollectionReference.document(id).get()
            .addOnSuccessListener { doc ->
                doc.toTeams()
                    .let(result::postValue)
            }
        return result
    }


}
