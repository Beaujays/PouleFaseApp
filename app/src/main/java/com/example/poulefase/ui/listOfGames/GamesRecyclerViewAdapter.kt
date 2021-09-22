package com.example.poulefase.ui.listOfGames

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.poulefase.R
import com.example.poulefase.data.objects.Games
import com.example.poulefase.ui.MainActivity

class GamesRecyclerViewAdapter (
    private val activity: MainActivity,
    private val games: LiveData<List<Games>>
) : RecyclerView.Adapter<GamesRecyclerViewAdapter.ViewHolder>()
{
    init
    {
        val dataObserver = Observer<List<Games>>
        {
            this.notifyDataSetChanged()
        }

        games.observe(activity, dataObserver)
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val awayTeamID: TextView = view.findViewById(R.id.awayTeamID)
        private var navController: NavController? = null
        var id: String? = null

/*
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

 */
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(activity)
            .inflate(R.layout.view_games_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("SiteDefectsRecyclerView", "onBindViewHolder: $position")
        val game = games.value?.get(position) ?: throw java.lang.IllegalStateException()

        // makes title of subject in list
        holder.id = game.AwayTeamID
        holder.awayTeamID.text = game.AwayTeamID
    }

    override fun getItemCount(): Int = games.value?.size ?: 0
}