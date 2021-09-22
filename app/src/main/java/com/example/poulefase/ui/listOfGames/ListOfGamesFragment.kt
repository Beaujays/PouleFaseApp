package com.example.poulefase.ui.listOfGames

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.poulefase.MyApplication
import com.example.poulefase.R
import com.example.poulefase.databinding.ListOfGamesFragmentBinding
import com.example.poulefase.databinding.PoolsViewFragmentBinding
import com.example.poulefase.ui.MainActivity
import com.example.poulefase.ui.listOfPools.ListOfPoolsViewModel
import com.example.poulefase.ui.listOfPools.ListOfPoolsViewModelFactory
import com.example.poulefase.ui.listOfPools.PoolsRecyclerViewAdapter

class ListOfGamesFragment : Fragment() {
    private val listOfGamesViewModel: ListOfGamesViewModel by viewModels {
        val application = requireActivity().application as MyApplication
        val gamesRepository = application.gamesRepository
        ListOfGamesViewModelFactory(gamesRepository)
    }

    private lateinit var binding: ListOfGamesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.list_of_games_fragment, container, false)
        Log.i("ListOfGamesViewFragment", "OnCreateView: ${listOfGamesViewModel.allGames}")
        val adapter = GamesRecyclerViewAdapter(
            activity as MainActivity,
            listOfGamesViewModel.allGames
        )
        binding.gamesRecycler.setHasFixedSize(true)
        binding.gamesRecycler.layoutManager = LinearLayoutManager(activity)
        binding.gamesRecycler.adapter = adapter

        return binding.root
    }

}