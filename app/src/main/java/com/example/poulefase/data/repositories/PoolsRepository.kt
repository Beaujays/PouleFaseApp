package com.example.poulefase.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.poulefase.data.objects.Pools
import com.example.poulefase.data.objects.Teams
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QueryDocumentSnapshot

class PoolsRepository(private val poolsCollectionReference: CollectionReference) {

    private val defaultPools = Pools("", "One moment please", "")

    private val all: MutableLiveData<List<Pools>> =
        MutableLiveData(listOf(defaultPools))

    // Post object on FireStore
    fun insert(pools: Pools)
    {
        poolsCollectionReference.document().set(
            pools.toPoolsData()
        ).addOnSuccessListener { getAll() }
    }

    // Get all from FireStore
    fun getAll(): LiveData<List<Pools>>
    {
        poolsCollectionReference.get()
            .addOnSuccessListener { documents ->
                documents
                    .map(QueryDocumentSnapshot::toPools)
                    .let(all::postValue)
            }
        return all
    }

    // Get by ID from FireStore
    fun getById(id: String): LiveData<Pools>
    {
        val result = MutableLiveData(defaultPools)
        poolsCollectionReference.document(id).get()
            .addOnSuccessListener { doc ->
                doc.toPools()
                    .let(result::postValue)
            }
        return result
    }
}