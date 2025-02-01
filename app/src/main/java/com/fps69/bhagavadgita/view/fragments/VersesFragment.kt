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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fps69.bhagavadgita.NetworkManger

import com.fps69.bhagavadgita.R
import com.fps69.bhagavadgita.databinding.FragmentVersesBinding
import com.fps69.bhagavadgita.view.adapter.AdapterVerses
import com.fps69.bhagavadgita.viewmodel.MainViewModel
import kotlinx.coroutines.launch


class VersesFragment : Fragment() {

    private lateinit var binding: FragmentVersesBinding
    private val mainViewmodel: MainViewModel by viewModels()
    private lateinit var adapterVerses: AdapterVerses
    private  var chapterNumber: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVersesBinding.inflate(layoutInflater)





        changeStatuesBarColor()

        getData()

        onReadMoreClicked()

        return binding.root
    }

    private fun getData() {
        val bundle = arguments
        val showRoomData = bundle?.getBoolean("showRoomData")
        chapterNumber= bundle?.getInt("chapterNumber")!!
        val verseCount= bundle?.getInt("verseCount")
        val chapterTitle= bundle?.getString("chapterTitle")
        val chapterDec= bundle?.getString("chapterDec")


        if(showRoomData==true){
            getDataFromRoom(chapterNumber)
        }

        else{
            getAndSetChapterDetails(chapterNumber,verseCount,chapterTitle,chapterDec)
            checkNetworkConnection(chapterNumber)
        }
    }

    private fun getAndSetChapterDetails(chapterNumber: Int, verseCount: Int?, chapterTitle: String?, chapterDec: String?) {

        binding.apply {
            tvChapterNumbers.text = "Chapter ${chapterNumber}"
            tvNumberOfVerses.text= verseCount.toString()
            tvChapterTitle.text = chapterTitle
            tvChaptersDescription.text = chapterDec
        }
    }

    private fun getDataFromRoom(chapterNumber: Int) {
        mainViewmodel.getAParticularChapter(chapterNumber).observe(viewLifecycleOwner){savedChapters->

            val chapterNumber_temp = savedChapters.chapter_number
            val verseCount= savedChapters.verses_count
            val chapterTitle= savedChapters.name_translated
            val chapterDec= savedChapters.chapter_summary

            getAndSetChapterDetails(chapterNumber_temp,verseCount,chapterTitle,chapterDec)

            val verseList = savedChapters.verses

            val fromRoom = true // Mark this value true because we are getting data from RoomDb(this for handel click on iteView of recyclerView)
            setuprecyclerView(verseList,fromRoom)



        }
    }

    private fun onReadMoreClicked() {
        var isExpanded = false
        binding.tvSeeMore.setOnClickListener{
            if(!isExpanded){
                binding.tvChaptersDescription.maxLines = 50
                binding.tvSeeMore.text = "See Less ....."
                isExpanded=true
            }
            else{
                binding.tvChaptersDescription.maxLines=4
                binding.tvSeeMore.text = "Read More ....."
                isExpanded=false
            }
        }
    }

    private fun checkNetworkConnection(chapterNumber: Int) {

        val networkManger = NetworkManger(requireContext())
        networkManger.observe(viewLifecycleOwner) { connection ->
            if (connection == true) {
                getVerses(chapterNumber)
                binding.apply {
                    shimmer.visibility = View.VISIBLE
                    rvVerses.visibility = View.VISIBLE
                    tvShowingMessage.visibility = View.GONE
                }
            } else {
                binding.apply {
                    shimmer.visibility = View.GONE
                    rvVerses.visibility = View.GONE
                    tvShowingMessage.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun getVerses(chapterNumber: Int) {
        lifecycleScope.launch{
            mainViewmodel.getVerses(chapterNumber).collect { verseItemList ->

                val verseList = arrayListOf<String>()
                for (currentVerse in verseItemList) {
                    for (des in currentVerse.translations) {
                        if(des.language == "english"){
                            verseList.add(des.description)
                            break
                        }
                    }
                }
                val fromRoom = false // Mark this value false because we are getting data from API(this for handel click on iteView of recyclerView)
                setuprecyclerView(verseList,fromRoom)
            }
        }
    }

    private fun setuprecyclerView(verseList: List<String>, fromRoom: Boolean) {
        adapterVerses = AdapterVerses(::onVersesItemClicked,fromRoom)
        binding.rvVerses.adapter = adapterVerses
        adapterVerses.differ.submitList(verseList)
        binding.shimmer.visibility= View.GONE
        binding.rvVerses.visibility= View.VISIBLE


    }

    private  fun onVersesItemClicked(verse : String , position: Int ){


        val bundle = Bundle()

        val comingFromRoom = false
        bundle.putBoolean("comingFromRoom", comingFromRoom)
        bundle.putInt("verseNumber", position)
        bundle.putInt("chapterNumber", chapterNumber)
        findNavController().navigate(R.id.action_versesFragment_to_verseDetalisFragment, bundle)

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