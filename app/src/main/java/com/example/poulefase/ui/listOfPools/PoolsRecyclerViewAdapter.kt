package com.example.poulefase.ui.listOfPools

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.poulefase.data.objects.Pools
import com.example.poulefase.R
import com.example.poulefase.ui.MainActivity
import java.lang.IllegalStateException

@SuppressLint("NotifyDataSetChanged")
class PoolsRecyclerViewAdapter
    (
    private val activity: MainActivity,
    private val pools: LiveData<List<Pools>>
    ) : RecyclerView.Adapter<PoolsRecyclerViewAdapter.ViewHolder>()
{
    init
    {
        val dataObserver = Observer<List<Pools>>
        {
            this.notifyDataSetChanged()
        }

        pools.observe(activity, dataObserver)
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val poolName: TextView = view.findViewById(R.id.poolNameText)
        private var navController: NavController? = null
        var id: String? = null


        init
        {
            view.setOnClickListener{
                val id: String = id ?: throw IllegalStateException()
                navController = Navigation.findNavController(view)
                navController!!.navigate(
                    //navigate to other fragment
                    ListOfPoolsViewFragmentDirections.actionListOfPoolsViewFragmentToDetailPoolFragment(id)
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(activity)
            .inflate(R.layout.view_pools_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("PoolsRecyclerView", "onBindViewHolder: $position")
        val pool = pools.value?.get(position) ?: throw IllegalStateException()

        // makes title of subject in list
        holder.id = pool.ID
        holder.poolName.text = pool.PoolName
    }

    override fun getItemCount(): Int = pools.value?.size ?: 0
}