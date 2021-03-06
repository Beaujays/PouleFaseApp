package com.example.poulefase.ui.listOfGames

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.poulefase.R
import com.example.poulefase.data.objects.Games
import com.example.poulefase.ui.MainActivity

@SuppressLint("NotifyDataSetChanged")
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
        val homeName: TextView = view.findViewById(R.id.homeName)
        val awayName: TextView = view.findViewById(R.id.awayName)
        val homePoints: TextView = view.findViewById(R.id.pointsHome)
        val awayPoints: TextView = view.findViewById(R.id.pointsAway)
        val homeGoals: TextView = view.findViewById(R.id.goalsHome)
        val awayGoals: TextView = view.findViewById(R.id.goalsAway)
        var id: String? = null

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(activity)
            .inflate(R.layout.view_games_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("GamesRecyclerView", "onBindViewHolder: $position")
        val game = games.value?.get(position) ?: throw java.lang.IllegalStateException()

        // makes title of subject in list
        holder.id = game.GameID
        holder.homeName.text = game.HomeName
        holder.awayName.text = game.AwayName
        holder.homePoints.text = game.PointsHomeTeam
        holder.awayPoints.text = game.PointsAwayTeam
        holder.homeGoals.text = game.GoalsHomeTeam
        holder.awayGoals.text = game.GoalsAwayTeam

    }

    override fun getItemCount(): Int = games.value?.size ?: 0
}