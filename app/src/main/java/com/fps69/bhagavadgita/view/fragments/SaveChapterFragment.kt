package com.fps69.bhagavadgita.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.fps69.bhagavadgita.R
import com.fps69.bhagavadgita.databinding.FragmentSaveChapterBinding
import com.fps69.bhagavadgita.modle.ChaptersItem
import com.fps69.bhagavadgita.view.adapter.AdapterChapters
import com.fps69.bhagavadgita.viewmodel.MainViewModel

class SaveChapterFragment : Fragment() {

    private lateinit var binding : FragmentSaveChapterBinding
    private val viewModel : MainViewModel by viewModels()
    private lateinit var adapterChapters: AdapterChapters

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentSaveChapterBinding.inflate(layoutInflater)


        getSavedChapters()

        return binding.root
    }

    private fun getSavedChapters() {
        viewModel.getSavedChapters().observe(viewLifecycleOwner){listOfSavedChapters->
            val chapterList = arrayListOf<ChaptersItem>()

            for(i in listOfSavedChapters){
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
                    i.verses_count)

                chapterList.add(chapterItem)
            }

            if(chapterList.isEmpty()){
                binding.apply {
                    shimmer.visibility=View.GONE
                    rvChapters.visibility=View.GONE
                    tvShowingMessage.visibility=View.VISIBLE
                }
            }
            else{
                binding.apply {
                    tvShowingMessage.visibility=View.GONE
                    shimmer.visibility=View.VISIBLE
                    setupRecyclerView(chapterList)

                }
            }
        }
    }

    private fun setupRecyclerView(chapterList: ArrayList<ChaptersItem>) {
        adapterChapters = AdapterChapters(::onChapterIVClicked, ::onFavoriteClicked)
        binding.rvChapters.adapter = adapterChapters
        adapterChapters.differ.submitList(chapterList)
        binding.shimmer.visibility = View.GONE
    }

    private fun onFavoriteClicked(chaptersItem: ChaptersItem) {

    }

    fun onChapterIVClicked(chaptersItem: ChaptersItem) {

    }


}


