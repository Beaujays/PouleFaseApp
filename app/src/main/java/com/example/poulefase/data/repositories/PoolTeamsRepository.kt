package com.example.poulefase.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.poulefase.data.objects.PoolTeams
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QueryDocumentSnapshot

class PoolTeamsRepository(private val poolTeamsCollectionReference: CollectionReference) {

    private val defaultPoolTeams = PoolTeams(
        "", "One moment please", "","","",
        "", "", "", "", "", "", ""
    )

    private val all: MutableLiveData<List<PoolTeams>> =
        MutableLiveData(listOf(defaultPoolTeams))

    // Post object on FireStore
    fun insert(poolTeams: PoolTeams) {
        poolTeamsCollectionReference.document().set(
            poolTeams.toPoolTeamsData()
        ).addOnSuccessListener { getAll() }
    }

    // Update object on FireStore
    fun update(poolTeams: PoolTeams) {
        poolTeamsCollectionReference.document(poolTeams.PoolTeamsID).set(
            poolTeams.toPoolTeamsData()
        ).addOnSuccessListener { getAll() }
    }

    // Get all from FireStore
    fun getAll(): LiveData<List<PoolTeams>> {
        poolTeamsCollectionReference.get()
            .addOnSuccessListener { documents ->
                documents
                    .map(QueryDocumentSnapshot::toPoolTeams)
                    .sortedByDescending { it.PointsFor }
                    .let(all::postValue)
            }
        return all
    }

    // Get object based on TeamID and PoolID from FireStore
    fun getTeamId(id: String, poolId: String): LiveData<PoolTeams>? {
        poolTeamsCollectionReference.get()
            .addOnSuccessListener { documents ->
                documents
                    .map(QueryDocumentSnapshot::toPoolTeams)
                    .let(all::postValue)
            }

        all.value?.forEach { res ->
            if (res.SequenceNb == id && res.PoolID == poolId) {
                return MutableLiveData(res)
            }
        }
        return null
    }
}