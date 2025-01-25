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
import kotlinx.coroutines.Dispatchers
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

        getAndSetChapterDetails()



        changeStatuesBarColor()

        checkNetworkConnection()

        onReadMoreClicked()

        return binding.root
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

    private fun getAndSetChapterDetails() {

        val bundle = arguments
        chapterNumber = bundle?.getInt("chapterNumber")!!

        binding.apply {
            tvChapterNumbers.text = "Chapter ${bundle?.getInt("chapterNumber")}"
            tvNumberOfVerses.text= bundle?.getInt("verseCount").toString()
            tvChapterTitle.text = bundle?.getString("chapterTitle")
            tvChaptersDescription.text = bundle?.getString("chapterDec")
        }

    }

    private fun checkNetworkConnection() {

        val networkManger = NetworkManger(requireContext())
        networkManger.observe(viewLifecycleOwner) { connection ->
            if (connection == true) {
                getVerses()
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

    private fun getVerses() {
        lifecycleScope.launch(Dispatchers.IO){
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
                setuprecyclerView(verseList)
            }
        }
    }

    private fun setuprecyclerView(  verseList: ArrayList<String>) {
        adapterVerses = AdapterVerses(::onVersesItemClicked)
        binding.rvVerses.adapter = adapterVerses
        adapterVerses.differ.submitList(verseList)
        binding.shimmer.visibility= View.GONE
        binding.rvVerses.visibility= View.VISIBLE


    }

    private  fun onVersesItemClicked(verse : String , position: Int ){


        val bundle = Bundle()

        bundle.putInt("verseNumber", position)
        bundle.putString("verse", verse)
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