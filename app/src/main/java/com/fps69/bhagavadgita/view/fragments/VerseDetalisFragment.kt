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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.fps69.bhagavadgita.NetworkManger

import com.fps69.bhagavadgita.R
import com.fps69.bhagavadgita.databinding.FragmentVerseDetalisBinding
import com.fps69.bhagavadgita.databinding.FragmentVersesBinding
import com.fps69.bhagavadgita.modle.VersesItem
import com.fps69.bhagavadgita.viewmodel.MainViewModel
import kotlinx.coroutines.launch


class VerseDetalisFragment : Fragment() {


    private lateinit var binding: FragmentVerseDetalisBinding
    private val mainViewModel : MainViewModel by viewModels()

    private var chapterNumber : Int = 0
    private var verseNumber : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentVerseDetalisBinding.inflate(layoutInflater)

        getAndSetAllAtribute()

        changeStatuesBarColor()

        checkNetworkConnection()

        onReadMoreClicked()




        return binding.root
    }


    private fun onReadMoreClicked() {
        var isExpanded = false
        binding.tvSeeMore.setOnClickListener{
            if(!isExpanded){
                binding.tvTextCommentary.maxLines = 150
                binding.tvSeeMore.text = "See Less ....."
                isExpanded=true
            }
            else{
                binding.tvTextCommentary.maxLines=4
                binding.tvSeeMore.text = "Read More ....."
                isExpanded=false
            }
        }
    }

    private fun getAndSetAllAtribute() {
        val bundle = arguments
        chapterNumber = bundle?.getInt("chapterNumber")!!
        verseNumber = bundle?.getInt("verseNumber")!!

        binding.tvVerseNumber.text = "||$chapterNumber.$verseNumber||"
    }

    private fun checkNetworkConnection() {

        val networkManager = NetworkManger(requireContext())
        networkManager.observe(viewLifecycleOwner){connection ->

            if(connection == true ){
                getVersesDetails()
                binding.apply {
                    tvVerseNumber.visibility=View.GONE
                    tvVersesText.visibility=View.GONE
                    tvTransliterationIfEnglish.visibility=View.GONE
                    tvWordIfEnglish.visibility=View.GONE
                    view.visibility=View.GONE
                    iv1.visibility=View.GONE
                    iv2.visibility=View.GONE
                    llBottom.visibility=View.GONE
                    progressBar.visibility=View.VISIBLE
                    clInternetConnection.visibility=View.GONE
                }

            }
            else{
                binding.apply {
                    tvVerseNumber.visibility=View.GONE
                    tvVersesText.visibility=View.GONE
                    tvTransliterationIfEnglish.visibility=View.GONE
                    tvWordIfEnglish.visibility=View.GONE
                    view.visibility=View.GONE
                    iv1.visibility=View.GONE
                    iv2.visibility=View.GONE
                    llBottom.visibility=View.GONE
                    progressBar.visibility=View.GONE
                    clInternetConnection.visibility=View.VISIBLE
                }
            }
        }
    }

    private fun getVersesDetails() {
        lifecycleScope.launch {
            mainViewModel.getVerseDetails(chapterNumber, verseNumber).collect{verseItem ->

                binding.apply {
                    tvVerseNumber.visibility=View.VISIBLE
                    tvVersesText.visibility=View.VISIBLE
                    tvTransliterationIfEnglish.visibility=View.VISIBLE
                    tvWordIfEnglish.visibility=View.VISIBLE
                    view.visibility=View.VISIBLE
                    iv1.visibility=View.VISIBLE
                    iv2.visibility=View.VISIBLE
                    llBottom.visibility=View.VISIBLE
                    progressBar.visibility=View.GONE
                }
                setAllAtribute(verseItem)

            }
        }
    }

    private fun setAllAtribute(verseItem: VersesItem) {

        binding.apply {
            tvVersesText.text = verseItem.text
            tvTransliterationIfEnglish.text = verseItem.transliteration
            tvWordIfEnglish.text= verseItem.word_meanings
        }


        val translations = verseItem.translations

        val translationsSize = translations.size

        if(translations. isNotEmpty()){
            binding.tvAuthorName.text=translations[0].author_name
            binding.tvText.text = translations[0].description
            binding.fabTranslationLeft.visibility= View.GONE

            if(translationsSize == 1){
                binding.fabTranslationRight.visibility= View.GONE
                binding.fabTranslationLeft.visibility= View.GONE
            }
            else{
                var i =0
                binding.fabTranslationRight.setOnClickListener {
                    if(i<translationsSize-1){
                        i++
                        binding.tvAuthorName.text=translations[i].author_name
                        binding.tvText.text = translations[i].description
                        binding.fabTranslationLeft.visibility= View.VISIBLE

                        if(i == translationsSize-1) binding.fabTranslationRight.visibility = View.GONE
                    }
                }


                binding.fabTranslationLeft.setOnClickListener {
                    if(i>0){
                        i--
                        binding.tvAuthorName.text=translations[i].author_name
                        binding.tvText.text = translations[i].description
                        binding.fabTranslationRight.visibility= View.VISIBLE

                        if(i == 0) binding.fabTranslationLeft.visibility = View.GONE
                    }
                }
            }
        }

        val commentary = verseItem.commentaries
        val commentarySize = commentary.size

        if(commentary.isNotEmpty()){
            binding.tvAuthorNameCommentary.text = commentary[0].author_name
            binding.tvTextCommentary.text = commentary[0].description
            binding.fabCommentaryLeft.visibility = View.GONE

            if(commentarySize ==1 ){
                binding.fabCommentaryRight.visibility= View.GONE
                binding.fabCommentaryLeft.visibility=View.GONE
            }
            else{
                var i =0
                binding.fabCommentaryRight.setOnClickListener {
                    if(i < commentarySize-1){
                        i++
                        binding.tvAuthorNameCommentary.text = commentary[i].author_name
                        binding.tvTextCommentary.text = commentary[i].description
                        binding.fabCommentaryLeft.visibility = View.VISIBLE

                        if(i == commentarySize-1 ) binding.fabCommentaryRight.visibility= View.GONE
                    }
                }

                binding.fabCommentaryLeft.setOnClickListener {
                    if(i>0){
                        i--
                        binding.tvAuthorNameCommentary.text = commentary[i].author_name
                        binding.tvTextCommentary.text = commentary[i].description
                        binding.fabCommentaryRight.visibility = View.VISIBLE


                        if(i == 0 ) binding.fabCommentaryLeft.visibility = View.GONE
                    }
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