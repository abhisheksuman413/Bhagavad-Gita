package com.fps69.bhagavadgita.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fps69.bhagavadgita.R

import com.fps69.bhagavadgita.databinding.FragmentSavedVerseBinding
import com.fps69.bhagavadgita.datasource.room.SavedVerses
import com.fps69.bhagavadgita.view.adapter.AdapterSavedVerses
import com.fps69.bhagavadgita.viewmodel.MainViewModel


class SavedVerseFragment : Fragment() {


    private lateinit var binding:FragmentSavedVerseBinding
    private val viewModel : MainViewModel by viewModels()
    private lateinit var savedVersesAdapter : AdapterSavedVerses

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentSavedVerseBinding.inflate(layoutInflater)

        changeStatuesBarColor()

        getSavedVersesFromRoom()



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

    private fun getSavedVersesFromRoom() {
        viewModel.getAllEnglishVerses().observe(viewLifecycleOwner){savedVerseList->

            if(savedVerseList.isEmpty()){
                binding.apply {
                    shimmer.visibility = View.GONE
                    rvVerses.visibility = View.GONE
                    tvShowingMessage.visibility = View.VISIBLE
                }
            }
            else{
                binding.apply {
                    tvShowingMessage.visibility = View.GONE
                    shimmer.visibility = View.VISIBLE
                    setupRecyclerView(savedVerseList)
                }
            }


        }
    }

    private fun setupRecyclerView(savedVerseList: List<SavedVerses>) {

        binding.rvVerses.visibility=View.VISIBLE
        savedVersesAdapter =AdapterSavedVerses(::onItemViewClicked)
        binding.rvVerses.adapter= savedVersesAdapter
        savedVersesAdapter.differ.submitList(savedVerseList)
        binding.shimmer.visibility= View.GONE

    }

    private fun onItemViewClicked(chapterNumber : Int , verseNumber: Int){

        val comingFromRoom = true
        val bundle = Bundle()
        bundle.putInt("chapterNumber", chapterNumber)
        bundle.putInt("verseNumber", verseNumber)
        bundle.putBoolean("comingFromRoom", comingFromRoom)

        findNavController().navigate(R.id.action_savedVerseFragment_to_verseDetalisFragment,bundle)

    }


}