package com.example.poulefase.ui.listOfPools

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
import com.example.poulefase.ui.MainActivity
import com.example.poulefase.databinding.PoolsViewFragmentBinding

class ListOfPoolsViewFragment : Fragment() {

    // Set late init and variables
    private val listOfPoolsViewModel: ListOfPoolsViewModel by viewModels {
        val application = requireActivity().application as MyApplication
        val poolsRepository = application.poolsRepository
        ListOfPoolsViewModelFactory(poolsRepository)
    }

    private lateinit var binding: PoolsViewFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.pools_view_fragment, container, false)
        Log.i("ListOfPoolsViewFragment", "OnCreateView: ${listOfPoolsViewModel.allPools}")
        val adapter = PoolsRecyclerViewAdapter(
            activity as MainActivity,
            listOfPoolsViewModel.allPools
        )
        binding.poolsRecycler.setHasFixedSize(true)
        binding.poolsRecycler.layoutManager = LinearLayoutManager(activity)
        binding.poolsRecycler.adapter = adapter

        // Navigate to add pool fragment
        binding.addNewPoolButton.setOnClickListener {
            findNavController().navigate(R.id.action_listOfPoolsViewFragment_to_createPoolFragment)
        }

        return binding.root
    }

}