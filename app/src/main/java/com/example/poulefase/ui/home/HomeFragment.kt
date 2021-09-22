package com.example.poulefase.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.poulefase.R
import com.example.poulefase.databinding.HomeFragmentBinding
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        // Log out from app
        binding.buttonLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            requireActivity().recreate()
        }

        return binding.root
    }
}