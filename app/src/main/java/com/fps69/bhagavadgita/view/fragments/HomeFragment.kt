package com.fps69.bhagavadgita.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.PrimaryKey
import com.fps69.bhagavadgita.NetworkManger
import com.fps69.bhagavadgita.R
import com.fps69.bhagavadgita.databinding.FragmentHomeBinding
import com.fps69.bhagavadgita.datasource.room.SavedChapters
import com.fps69.bhagavadgita.modle.ChaptersItem
import com.fps69.bhagavadgita.view.adapter.AdapterChapters
import com.fps69.bhagavadgita.viewmodel.MainViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    //    private lateinit var viewModel : MainViewModel >>> Upper wale ke place pe
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapterChapters: AdapterChapters


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

//        viewModel= ViewModelProvider(this).get(MainViewModel::class.java)

        changeStatuesBarColor()

        checkNetworkConnection()

        binding.ivSave.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
        }



        return binding.root
    }


    private fun onFavoriteClicked(chapterItem: ChaptersItem) {


        lifecycleScope.launch {
            viewModel.getVerses(chapterItem.chapter_number).collect { verseItemList ->

                val verseList = arrayListOf<String>()
                for (currentVerse in verseItemList) {
                    for (des in currentVerse.translations) {
                        if (des.language == "english") {
                            verseList.add(des.description)
                            break
                        }
                    }
                }

                val savedChapters = SavedChapters(
                    chapter_number=chapterItem.chapter_number,
                    chapter_summary=chapterItem.chapter_summary,
                    chapter_summary_hindi=chapterItem.chapter_summary_hindi,

                    id=chapterItem.id,
                    name= chapterItem.name,
                    name_meaning=chapterItem.name_meaning,
                    name_translated=chapterItem.name_translated,
                    name_transliterated=chapterItem.name_transliterated,
                    slug=chapterItem.slug,
                    verses_count=chapterItem.verses_count,
                    verses= verseList
                )

//                saveChaptersInRoomDB(savedChapters)
                viewModel.insertChapters(savedChapters)
                Toast.makeText(requireContext(),"${savedChapters.chapter_number} is Saved ",Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun checkNetworkConnection() {
        val networkManger = NetworkManger(requireContext())
        networkManger.observe(viewLifecycleOwner) { connection ->
            if (connection == true) {
                getAllChapters()
                binding.apply {
                    shimmer.visibility = View.VISIBLE
                    rvChapters.visibility = View.VISIBLE
                    tvShowingMessage.visibility = View.GONE
                }
            } else {
                binding.apply {
                    shimmer.visibility = View.GONE
                    rvChapters.visibility = View.GONE
                    tvShowingMessage.visibility = View.VISIBLE

                }
            }
        }

    }

    private fun getAllChapters() {

        lifecycleScope.launch {
            viewModel.getAllChapters().collect { ChapterList ->
                setupRecyclerView(ChapterList)
            }
        }

    }

    private fun onChapterIVClicked(chapterItem: ChaptersItem) {

        val bundle = Bundle()

        bundle.putInt("chapterNumber", chapterItem.chapter_number.toInt())
        bundle.putString("chapterTitle", chapterItem.name_translated)
        bundle.putString("chapterDec", chapterItem.chapter_summary)
        bundle.putInt("verseCount", chapterItem.verses_count.toInt())

        findNavController().navigate(R.id.action_homeFragment_to_versesFragment, bundle)

    }

    private fun setupRecyclerView(ChapterList: List<ChaptersItem>) {

        adapterChapters = AdapterChapters(::onChapterIVClicked, ::onFavoriteClicked)
        binding.rvChapters.adapter = adapterChapters
        adapterChapters.differ.submitList(ChapterList)
        binding.shimmer.visibility = View.GONE

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