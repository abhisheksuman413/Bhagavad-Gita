package com.fps69.bhagavadgita.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fps69.bhagavadgita.NetworkManger

import com.fps69.bhagavadgita.R
import com.fps69.bhagavadgita.databinding.FragmentVersesBinding
import com.fps69.bhagavadgita.viewmodel.MainViewModel
import kotlinx.coroutines.launch


class VersesFragment : Fragment() {

    private lateinit var binding : FragmentVersesBinding
    private val mainViewmodel : MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVersesBinding.inflate(layoutInflater)


        changeStatuesBarColor()

        checkNetworkConnection()

        return binding.root
    }

    private fun checkNetworkConnection() {

        val networkManger = NetworkManger(requireContext())
        networkManger.observe(viewLifecycleOwner){connection ->
            if(connection ==true){
                getVerses()
                binding.apply {
                    shimmer.visibility = View.VISIBLE
                    rvVerses.visibility = View.VISIBLE
                    tvShowingMessage.visibility = View.GONE
                }
            }
            else{
                binding.apply {
                    shimmer.visibility = View.GONE
                    rvVerses.visibility = View.GONE
                    tvShowingMessage.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun getVerses() {
        lifecycleScope.launch {
            mainViewmodel.getVerses(1).collect{
                for( i in it){
                    Log.d("sdsjvdv", i.toString())
                }
            }
        }
    }

    private fun changeStatuesBarColor() {
        val window = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        if (window != null) {
            WindowCompat.getInsetsController(window, window.decorView).apply {
                isAppearanceLightStatusBars = true
            }
        }
    }


}