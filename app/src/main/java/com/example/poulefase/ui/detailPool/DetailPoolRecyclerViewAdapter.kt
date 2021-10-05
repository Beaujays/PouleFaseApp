package com.example.poulefase.ui.detailPool

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
import com.example.poulefase.data.objects.PoolTeams
import com.example.poulefase.ui.MainActivity

@SuppressLint("NotifyDataSetChanged")
class DetailPoolRecyclerViewAdapter (
    private val activity: MainActivity,
    private val poolTeams: LiveData<List<PoolTeams>>
) : RecyclerView.Adapter<DetailPoolRecyclerViewAdapter.ViewHolder>()
{
    init
    {
        val dataObserver = Observer<List<PoolTeams>>
        {
            this.notifyDataSetChanged()
        }

        poolTeams.observe(activity, dataObserver)
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val detailFatigue: TextView = view.findViewById(R.id.detailPool_fatigue_Text)
        val detailStrength: TextView = view.findViewById(R.id.detailPool_strength_Text)
        val detailPointsFor: TextView = view.findViewById(R.id.detailPool_pointsFor_Text)
        val detailPointsAgainst: TextView = view.findViewById(R.id.detailPool_pointsAgainst_Text)
        val detailGoalsFor: TextView = view.findViewById(R.id.detailPool_goalsFor_Text)
        val detailGoalsAgainst: TextView = view.findViewById(R.id.detailPool_goalsAgainst_Text)
        val detailTeamName: TextView = view.findViewById(R.id.detailPool_teamName_text)
        //private var navController: NavController? = null
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
            .inflate(R.layout.view_detailpool_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("SiteDefectsRecyclerView", "onBindViewHolder: $position")
        val poolTeam = poolTeams.value?.get(position) ?: throw java.lang.IllegalStateException()

        // makes title of subject in list
        holder.id = poolTeam.PoolTeamsID
        holder.detailFatigue.text = poolTeam.FatiguePercentage
        holder.detailPointsFor.text = poolTeam.PointsFor.toString()
        holder.detailPointsAgainst.text = poolTeam.PointsAgainst.toString()
        holder.detailGoalsFor.text = poolTeam.GoalsFor.toString()
        holder.detailGoalsAgainst.text = poolTeam.GoalsAgainst.toString()
        holder.detailTeamName.text = poolTeam.TeamName
        holder.detailStrength.text = poolTeam.Strength
    }

    override fun getItemCount(): Int = poolTeams.value?.size ?: 0


}