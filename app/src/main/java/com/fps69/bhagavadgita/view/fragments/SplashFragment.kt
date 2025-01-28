package com.fps69.bhagavadgita.view.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.findNavController
import com.fps69.bhagavadgita.R


class SplashFragment : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



        changeStatuesBarColor()
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        },5000)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun changeStatuesBarColor() {
        val window = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.splash)
        if (window != null) {
            WindowCompat.getInsetsController(window, window.decorView).apply {
                isAppearanceLightStatusBars = true
            }
        }
    }


}