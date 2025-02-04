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

import com.fps69.bhagavadgita.databinding.FragmentSaveChapterBinding
import com.fps69.bhagavadgita.modle.ChaptersItem
import com.fps69.bhagavadgita.view.adapter.AdapterChapters
import com.fps69.bhagavadgita.viewmodel.MainViewModel

class SaveChapterFragment : Fragment() {

    private lateinit var binding: FragmentSaveChapterBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapterChapters: AdapterChapters

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentSaveChapterBinding.inflate(layoutInflater)

        changeStatuesBarColor()


        getSavedChapters()

        return binding.root
    }

    private fun changeStatuesBarColor() {

        val window = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.splash2)
        if (window != null) {
            WindowCompat.getInsetsController(window, window.decorView).apply {
                isAppearanceLightStatusBars = true
            }
        }
    }

    private fun getSavedChapters() {
        viewModel.getSavedChapters().observe(viewLifecycleOwner) { listOfSavedChapters ->
            val chapterList = arrayListOf<ChaptersItem>()

            for (i in listOfSavedChapters) {
                val chapterItem = ChaptersItem(
                    i.chapter_number,
                    i.chapter_summary,
                    i.chapter_summary_hindi,
                    i.id,
                    i.name,
                    i.name_meaning,
                    i.name_translated,
                    i.name_transliterated,
                    i.slug,
                    i.verses_count
                )

                chapterList.add(chapterItem)
            }

            if (chapterList.isEmpty()) {
                binding.apply {
                    shimmer.visibility = View.GONE
                    rvChapters.visibility = View.GONE
                    tvShowingMessage.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    tvShowingMessage.visibility = View.GONE
                    shimmer.visibility = View.VISIBLE
                    setupRecyclerView(chapterList)

                }
            }
        }
    }

    private fun setupRecyclerView(chapterList: ArrayList<ChaptersItem>) {
        binding.rvChapters.visibility=View.VISIBLE
        adapterChapters = AdapterChapters(
            ::onChapterIVClicked,
            ::onFavoriteClicked,
            false,
            ::onFavoriteFilledClicked,
            viewModel
        )
        binding.rvChapters.adapter = adapterChapters
        adapterChapters.differ.submitList(chapterList)
        binding.shimmer.visibility = View.GONE
    }


    fun onChapterIVClicked(chaptersItem: ChaptersItem) {
        val bundle = Bundle()
        bundle.putInt("chapterNumber", chaptersItem.chapter_number)
        bundle.putBoolean("showRoomData", true)
        findNavController().navigate(R.id.action_saveChapterFragment_to_versesFragment, bundle)
    }

    private fun onFavoriteClicked(chaptersItem: ChaptersItem) {

    }

    private fun onFavoriteFilledClicked(chapter:ChaptersItem){

    }


}


