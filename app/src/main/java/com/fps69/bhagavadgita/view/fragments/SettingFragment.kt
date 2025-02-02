package com.fps69.bhagavadgita.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.findNavController
import com.fps69.bhagavadgita.R
import com.fps69.bhagavadgita.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSettingBinding.inflate(layoutInflater)
        changeStatuesBarColor()


        navigateFormSettingFragment()




        return binding.root
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

    private fun navigateFormSettingFragment() {
        binding.apply {
            llSavedVerses.setOnClickListener {
                findNavController().navigate(R.id.action_settingFragment_to_savedVerseFragment)
            }
            llSavedChapter.setOnClickListener {
                findNavController().navigate(R.id.action_settingFragment_to_saveChapterFragment)
            }
        }
    }

}