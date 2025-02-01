package com.fps69.bhagavadgita.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        getSavedVersesFromRoom()



        return binding.root
    }

    private fun getSavedVersesFromRoom() {
        viewModel.getAllEnglishVerses().observe(viewLifecycleOwner){savedVerseList->

            setupRecyclerView(savedVerseList)
        }
    }

    private fun setupRecyclerView(savedVerseList: List<SavedVerses>) {

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