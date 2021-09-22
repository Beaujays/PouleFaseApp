package com.example.poulefase.ui.detailPool

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.poulefase.MyApplication
import com.example.poulefase.R
import com.example.poulefase.data.objects.Games
import com.example.poulefase.data.objects.GamesPrograms
import com.example.poulefase.data.objects.PoolTeams
import com.example.poulefase.data.objects.Pools
import com.example.poulefase.databinding.DetailPoolFragmentBinding
import com.example.poulefase.ui.MainActivity


class DetailPoolFragment : Fragment() {

    // Set late init and variables
    private val detailPoolViewModel: DetailPoolViewModel by viewModels {
        val application = requireActivity().application as MyApplication
        val poolTeamsRepository = application.poolTeamsRepository
        val poolsRepository = application.poolsRepository
        val gamesRepository = application.gamesRepository
        DetailPoolViewModelFactory(poolTeamsRepository, poolsRepository, gamesRepository, poolID)
    }

    private lateinit var binding: DetailPoolFragmentBinding
    private var poolID: String = ""
    private lateinit var homePoolTeam: LiveData<PoolTeams>
    private lateinit var awayPoolTeam: LiveData<PoolTeams>
    private var pool: LiveData<Pools>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            // Get poolID from arguments
            poolID = DetailPoolFragmentArgs.fromBundle(requireArguments()).id
            // Get Pools object
            pool = detailPoolViewModel.pool
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.detail_pool_fragment,
                container,
                false
            )

        binding =
            DataBindingUtil.inflate(inflater, R.layout.detail_pool_fragment, container, false)
        Log.i("DetailPoolFragment", "OnCreateView: ${detailPoolViewModel.allPoolTeams}")
        val adapter = DetailPoolRecyclerViewAdapter(
            activity as MainActivity,
            detailPoolViewModel.allPoolTeams
        )
        binding.detailPoolRecycler.setHasFixedSize(true)
        binding.detailPoolRecycler.layoutManager = LinearLayoutManager(activity)
        binding.detailPoolRecycler.adapter = adapter

        // Create schedule for pool
        val game1 = GamesPrograms(1,1,2)
        val game2 = GamesPrograms(2,1,3)
        val game3 = GamesPrograms(3,1,4)
        val game4 = GamesPrograms(4,2,3)
        val game5 = GamesPrograms(5,2,4)
        val game6 = GamesPrograms(6,3,4)

        // Set games in a mutable list
        val gamesList: MutableList<GamesPrograms> = mutableListOf()
        gamesList.add(game1)
        gamesList.add(game2)
        gamesList.add(game3)
        gamesList.add(game4)
        gamesList.add(game5)
        gamesList.add(game6)

        // Go to list of played games
        binding.listGameSButton.setOnClickListener{
            findNavController().navigate(R.id.action_nav_detailPool_to_listOfGamesFragment)
        }

        // Generates the results per pool
        binding.startGameButton.setOnClickListener {

            /*
            * Iterate through gamesList and
            * Check which team is stronger
            * When stronger the team that one receives 3 points, the loser 0
            * */

            gamesList.forEach{ game ->

                homePoolTeam = detailPoolViewModel.getTeamID(game.homeGame.toString(), pool?.value?.PoolID.toString())!!
                awayPoolTeam = detailPoolViewModel.getTeamID(game.awayGame.toString(), pool?.value?.PoolID.toString())!!

                if (homePoolTeam.value?.Strength!! > awayPoolTeam.value?.Strength!!) {
                    // Create a new game record
                    val games = Games(
                        "",
                        poolID,
                        homePoolTeam.value!!.TeamName,
                        awayPoolTeam.value!!.TeamName,
                        "1",
                        "0",
                        "3",
                        "0",
                        "",""
                    )
                    val pointsForHome: Int = homePoolTeam.value!!.PointsFor.toInt() + 3
                    val pointsForAway: Int = awayPoolTeam.value!!.PointsFor.toInt() + 0
                    val pointsAgainstHome: Int = homePoolTeam.value!!.PointsAgainst.toInt() + 0
                    val pointsAgainstAway: Int = awayPoolTeam.value!!.PointsAgainst.toInt() + 3
                    val goalsForHome: Int = homePoolTeam.value!!.GoalsFor.toInt() + 1
                    val goalsForAway: Int = awayPoolTeam.value!!.GoalsFor.toInt() + 0
                    val goalsAgainstHome: Int = homePoolTeam.value!!.GoalsAgainst.toInt() + 0
                    val goalsAgainstAway: Int = awayPoolTeam.value!!.GoalsAgainst.toInt() + 1
                    val updateHomePoolTeam = PoolTeams(
                        homePoolTeam.value!!.PoolTeamsID,
                        "",
                        homePoolTeam.value!!.SequenceNb,
                        homePoolTeam.value!!.PoolID,
                        homePoolTeam.value!!.TeamID,
                        homePoolTeam.value!!.TeamName,
                        pointsForHome.toString(),
                        pointsAgainstHome.toString(),
                        goalsForHome.toString(),
                        goalsAgainstHome.toString(),
                        homePoolTeam.value!!.FatiguePercentage,
                        homePoolTeam.value!!.Strength
                    )

                    val updateAwayPoolTeam = PoolTeams(
                        awayPoolTeam.value!!.PoolTeamsID,
                        "",
                        awayPoolTeam.value!!.SequenceNb,
                        awayPoolTeam.value!!.PoolID,
                        awayPoolTeam.value!!.TeamID,
                        awayPoolTeam.value!!.TeamName,
                        pointsForAway.toString(),
                        pointsAgainstAway.toString(),
                        goalsForAway.toString(),
                        goalsAgainstAway.toString(),
                        awayPoolTeam.value!!.FatiguePercentage,
                        awayPoolTeam.value!!.Strength
                    )
                    // Save game record in FireStore
                    detailPoolViewModel.insertGames(games)
                    detailPoolViewModel.updatePoolTeams(updateHomePoolTeam)
                    detailPoolViewModel.updatePoolTeams(updateAwayPoolTeam)

                }
                if (homePoolTeam.value?.Strength!! < awayPoolTeam.value?.Strength!!) {
                    val games = Games(
                        "",
                        poolID,
                        homePoolTeam.value!!.TeamName,
                        awayPoolTeam.value!!.TeamName,
                        "0",
                        "1",
                        "0",
                        "3",
                        "",""
                    )

                    val pointsForHome: Int = homePoolTeam.value!!.PointsFor.toInt() + 0
                    val pointsForAway: Int = awayPoolTeam.value!!.PointsFor.toInt() + 3
                    val pointsAgainstHome: Int = homePoolTeam.value!!.PointsAgainst.toInt() + 3
                    val pointsAgainstAway: Int = awayPoolTeam.value!!.PointsAgainst.toInt() + 0
                    val goalsForHome: Int = homePoolTeam.value!!.GoalsFor.toInt() + 0
                    val goalsForAway: Int = awayPoolTeam.value!!.GoalsFor.toInt() + 1
                    val goalsAgainstHome: Int = homePoolTeam.value!!.GoalsAgainst.toInt() + 1
                    val goalsAgainstAway: Int = awayPoolTeam.value!!.GoalsAgainst.toInt() + 0
                    val updateHomePoolTeam = PoolTeams(
                        homePoolTeam.value!!.PoolTeamsID,
                        "",
                        homePoolTeam.value!!.SequenceNb,
                        homePoolTeam.value!!.PoolID,
                        homePoolTeam.value!!.TeamID,
                        homePoolTeam.value!!.TeamName,
                        pointsForHome.toString(),
                        pointsAgainstHome.toString(),
                        goalsForHome.toString(),
                        goalsAgainstHome.toString(),
                        homePoolTeam.value!!.FatiguePercentage,
                        homePoolTeam.value!!.Strength
                    )

                    val updateAwayPoolTeam = PoolTeams(
                        awayPoolTeam.value!!.PoolTeamsID,
                        "",
                        awayPoolTeam.value!!.SequenceNb,
                        awayPoolTeam.value!!.PoolID,
                        awayPoolTeam.value!!.TeamID,
                        awayPoolTeam.value!!.TeamName,
                        pointsForAway.toString(),
                        pointsAgainstAway.toString(),
                        goalsForAway.toString(),
                        goalsAgainstAway.toString(),
                        awayPoolTeam.value!!.FatiguePercentage,
                        awayPoolTeam.value!!.Strength
                    )
                    // Save game record in FireStore
                    detailPoolViewModel.insertGames(games)
                    detailPoolViewModel.updatePoolTeams(updateHomePoolTeam)
                    detailPoolViewModel.updatePoolTeams(updateAwayPoolTeam)
                }
                if (homePoolTeam.value?.Strength!! == awayPoolTeam.value?.Strength!!) {
                    val games = Games(
                        "",
                        poolID,
                        homePoolTeam.value!!.TeamName,
                        awayPoolTeam.value!!.TeamName,
                        "1",
                        "0",
                        "1",
                        "1",
                        "",""
                    )

                    val pointsForHome: Int = homePoolTeam.value!!.PointsFor.toInt() + 0
                    val pointsForAway: Int = awayPoolTeam.value!!.PointsFor.toInt() + 0
                    val pointsAgainstHome: Int = homePoolTeam.value!!.PointsAgainst.toInt() + 0
                    val pointsAgainstAway: Int = awayPoolTeam.value!!.PointsAgainst.toInt() + 0
                    val goalsForHome: Int = homePoolTeam.value!!.GoalsFor.toInt() + 1
                    val goalsForAway: Int = awayPoolTeam.value!!.GoalsFor.toInt() + 1
                    val goalsAgainstHome: Int = homePoolTeam.value!!.GoalsAgainst.toInt() + 1
                    val goalsAgainstAway: Int = awayPoolTeam.value!!.GoalsAgainst.toInt() + 1
                    val updateHomePoolTeam = PoolTeams(
                        homePoolTeam.value!!.PoolTeamsID,
                        "",
                        homePoolTeam.value!!.SequenceNb,
                        homePoolTeam.value!!.PoolID,
                        homePoolTeam.value!!.TeamID,
                        homePoolTeam.value!!.TeamName,
                        pointsForHome.toString(),
                        pointsAgainstHome.toString(),
                        goalsForHome.toString(),
                        goalsAgainstHome.toString(),
                        homePoolTeam.value!!.FatiguePercentage,
                        homePoolTeam.value!!.Strength
                    )

                    val updateAwayPoolTeam = PoolTeams(
                        awayPoolTeam.value!!.PoolTeamsID,
                        "",
                        awayPoolTeam.value!!.SequenceNb,
                        awayPoolTeam.value!!.PoolID,
                        awayPoolTeam.value!!.TeamID,
                        awayPoolTeam.value!!.TeamName,
                        pointsForAway.toString(),
                        pointsAgainstAway.toString(),
                        goalsForAway.toString(),
                        goalsAgainstAway.toString(),
                        awayPoolTeam.value!!.FatiguePercentage,
                        awayPoolTeam.value!!.Strength
                    )
                    // Save game record in FireStore
                    detailPoolViewModel.insertGames(games)
                    detailPoolViewModel.updatePoolTeams(updateHomePoolTeam)
                    detailPoolViewModel.updatePoolTeams(updateAwayPoolTeam)
                }
            }
        }
        return binding.root
    }

}
