package com.example.poulefase.ui.createPool

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.example.poulefase.data.objects.Pools
import com.example.poulefase.MyApplication
import com.example.poulefase.R
import com.example.poulefase.data.objects.PoolTeams
import com.example.poulefase.data.objects.Teams
import com.example.poulefase.databinding.CreatePoolFragmentBinding

@Suppress("DEPRECATION")
class CreatePoolFragment : Fragment() {

    private val createPoolViewModel: CreatePoolViewModel by viewModels {
        val application = requireActivity().application as MyApplication
        val poolsRepository = application.poolsRepository
        val teamsRepository = application.teamsRepository
        val poolTeamsRepository = application.poolTeamsRepository
        CreatePoolViewModelFactory(poolsRepository,teamsRepository, poolTeamsRepository)
    }


    // Set late init and variables
    private lateinit var binding: CreatePoolFragmentBinding
    private lateinit var poolName: EditText
    private lateinit var list: LiveData<List<Teams>>
    private lateinit var pool: Pools


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.create_pool_fragment,
            container,
            false
        )

        // Binding layout field with variable value
        poolName = binding.editTextPoolName
        var sequenceNb = 0
        list = createPoolViewModel.allTeams

        // Button to create a new Pools object
        binding.buttonSavePool.setOnClickListener {
            if (TextUtils.isEmpty(poolName.text)) {
                val toast = Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT)
                toast.show()
            } else {

                // Save Pools in FireStore
                Log.i("buttonSave", "Not empty filled with ${poolName.text}")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    pool = Pools(
                            "",
                             getRandomString(10),
                            poolName.text.toString(),
                    )
                    // Add in FireStore
                    createPoolViewModel.insert(pool)

                    list.value?.forEach { teams ->
                        sequenceNb += 1
                        val poolTeams = PoolTeams(
                                "",
                                "0",
                                sequenceNb.toString(),
                                pool.PoolID,
                                teams.TeamID,
                                teams.TeamName,
                                0,
                                0,
                                0,
                                0,
                                "100",
                                teams.Strength
                        )
                        createPoolViewModel.insertPoolTeams(poolTeams)
                    }
                }

            }
            findNavController().navigate(R.id.action_createPoolFragment_to_listOfPoolsViewFragment)
        }
        return binding.root
    }

    // Get random string for PoolID
    private fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
    }
}