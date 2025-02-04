package com.fps69.bhagavadgita.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fps69.bhagavadgita.NetworkManger

import com.fps69.bhagavadgita.R
import com.fps69.bhagavadgita.databinding.FragmentVerseDetalisBinding
import com.fps69.bhagavadgita.datasource.room.SavedVerses
import com.fps69.bhagavadgita.modle.VersesItem
import com.fps69.bhagavadgita.viewmodel.MainViewModel
import kotlinx.coroutines.launch


class VerseDetalisFragment : Fragment() {


    private lateinit var binding: FragmentVerseDetalisBinding
    private val mainViewModel: MainViewModel by viewModels()

//    private var chapterNumber: Int = 0
//    private var verseNumber: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentVerseDetalisBinding.inflate(layoutInflater)


        changeStatuesBarColor()


        onReadMoreClicked()

        getData()








        return binding.root
    }



    // This function mange visibility of Favorite Button through Shared Preference
    private fun manageVisibilityOfFavoriteButton(chapterNumber: Int, verseNumber: Int) {
        val key = "${chapterNumber}.${verseNumber}"

        val keys_In_SharedPreference = mainViewModel.getAllSaveVersesFromSharedPreference()

        if(keys_In_SharedPreference.contains(key)){
            binding.apply {
                ivFavoriteVerse.visibility = View.GONE
                ivFavoriteVerseFilled.visibility = View.VISIBLE
            }
        }
        else{
            binding.apply {
                ivFavoriteVerse.visibility = View.VISIBLE
                ivFavoriteVerseFilled.visibility = View.GONE
            }
        }
    }

    private fun getData() {
        val bundle = arguments
        val comingFromRoom = bundle?.getBoolean("comingFromRoom")
        val chapterNumber = bundle?.getInt("chapterNumber")!!
        val verseNumber = bundle?.getInt("verseNumber")!!

        manageVisibilityOfFavoriteButton(chapterNumber,verseNumber)  //mange visibility of Favorite Button through Shared Preference



        if (comingFromRoom == true) {
            binding.apply {
                ivFavoriteVerse.visibility = View.GONE
                ivFavoriteVerseFilled.visibility = View.GONE
                progressBar.visibility=View.VISIBLE
            }
            if (chapterNumber != null && verseNumber != null) {
                getDataFromRoom(chapterNumber, verseNumber) // get data from roomDB and proceed for further task
            }

        } else {
            if (chapterNumber != null && verseNumber != null) {
                checkNetworkConnection(chapterNumber, verseNumber) // check network connection and proceed for further task

            }
        }
    }

    private fun getDataFromRoom(chapterNumber: Int, verseNumber: Int) {

        mainViewModel.getParticularVerse(chapterNumber, verseNumber)
            .observe(viewLifecycleOwner) { savedVerseItem ->

                val verseItem = VersesItem(
                    chapter_number = savedVerseItem.chapter_number,
                    commentaries = savedVerseItem.commentaries,
                    id = savedVerseItem.id,
                    slug = savedVerseItem.slug,
                    text = savedVerseItem.text,
                    translations = savedVerseItem.translations,
                    transliteration = savedVerseItem.transliteration,
                    verse_number = savedVerseItem.verse_number,
                    word_meanings = savedVerseItem.word_meanings
                )
                setAllAtribute(verseItem)
                binding.progressBar.visibility=View.GONE
            }

    }

    private fun onIvFavoriteVerseClick(verseItem: VersesItem) {

        val savedVerses = SavedVerses(
            chapter_number = verseItem.chapter_number,
            commentaries = verseItem.commentaries,
            id = verseItem.id,
            slug = verseItem.slug,
            text = verseItem.text,
            translations = verseItem.translations,
            transliteration = verseItem.transliteration,
            verse_number = verseItem.verse_number,
            word_meanings = verseItem.word_meanings
        )
        binding.ivFavoriteVerse.setOnClickListener {
            lifecycleScope.launch {

                // Insert in RoomDB
                mainViewModel.insertEnglishVerse(savedVerses)


                // Insert in Shared Preference through key
                val key = "${savedVerses.chapter_number}.${savedVerses.verse_number}"
                mainViewModel.putSaveVersesInSharedPreference(key, savedVerses.id)


                // Handel visibility of Favorite Button
                 binding.apply {
                     ivFavoriteVerse.visibility = View.GONE
                     ivFavoriteVerseFilled.visibility = View.VISIBLE
                 }
                Toast.makeText(
                    requireContext(),
                    "${savedVerses.chapter_number}.${savedVerses.verse_number} verse is Saved ",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    private fun onReadMoreClicked() {
        var isExpanded = false
        binding.tvSeeMore.setOnClickListener {
            if (!isExpanded) {
                binding.tvTextCommentary.maxLines = 150
                binding.tvSeeMore.text = "See Less ....."
                isExpanded = true
            } else {
                binding.tvTextCommentary.maxLines = 4
                binding.tvSeeMore.text = "Read More ....."
                isExpanded = false
            }
        }
    }



    private fun checkNetworkConnection(chapterNumber: Int, verseNumber: Int) {

        val networkManager = NetworkManger(requireContext())
        networkManager.observe(viewLifecycleOwner) { connection ->

            if (connection == true) {
                getVersesDetails(chapterNumber, verseNumber)
                binding.apply {
                    tvVerseNumber.visibility = View.GONE
                    tvVersesText.visibility = View.GONE
                    tvTransliterationIfEnglish.visibility = View.GONE
                    tvWordIfEnglish.visibility = View.GONE
                    view.visibility = View.GONE
                    iv1.visibility = View.GONE
                    iv2.visibility = View.GONE
                    llBottom.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    clInternetConnection.visibility = View.GONE
                }

            } else {
                binding.apply {
                    tvVerseNumber.visibility = View.GONE
                    tvVersesText.visibility = View.GONE
                    tvTransliterationIfEnglish.visibility = View.GONE
                    tvWordIfEnglish.visibility = View.GONE
                    view.visibility = View.GONE
                    iv1.visibility = View.GONE
                    iv2.visibility = View.GONE
                    llBottom.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    clInternetConnection.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getVersesDetails(chapterNumber: Int, verseNumber: Int) {
        lifecycleScope.launch {
            mainViewModel.getVerseDetails(chapterNumber, verseNumber).collect { verseItem ->

                binding.apply {
                    tvVerseNumber.visibility = View.VISIBLE
                    tvVersesText.visibility = View.VISIBLE
                    tvTransliterationIfEnglish.visibility = View.VISIBLE
                    tvWordIfEnglish.visibility = View.VISIBLE
                    view.visibility = View.VISIBLE
                    iv1.visibility = View.VISIBLE
                    iv2.visibility = View.VISIBLE
                    llBottom.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
                setAllAtribute(verseItem)
                onIvFavoriteVerseClick(verseItem)
                onIvFavoriteVerseFilledClick(verseItem)

            }
        }
    }

    private fun onIvFavoriteVerseFilledClick(verseItem: VersesItem){

        binding.ivFavoriteVerseFilled.setOnClickListener {
            lifecycleScope.launch {
                // Delete from RoomDB
                mainViewModel.deleteAParticularVerse(verseItem.chapter_number, verseItem.verse_number)

                // Delete from Shared Preference through key
                val key = "${verseItem.chapter_number}.${verseItem.verse_number}"
                mainViewModel.deleteSaveVersesFromSharedPreference(key)


                // Handel visibility of Favorite Button
                binding.apply {
                    ivFavoriteVerse.visibility = View.VISIBLE
                    ivFavoriteVerseFilled.visibility = View.GONE
                }

                Toast.makeText(requireContext(),"${verseItem.chapter_number}.${verseItem.verse_number} verse is deleted form Saved Verses ",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setAllAtribute(verseItem: VersesItem) {

        binding.apply {
            tvVerseNumber.text = "||${verseItem.chapter_number}.${verseItem.verse_number}||"
            tvVersesText.text = verseItem.text
            tvTransliterationIfEnglish.text = verseItem.transliteration
            tvWordIfEnglish.text = verseItem.word_meanings
        }


        val translations = verseItem.translations

        val translationsSize = translations.size

        if (translations.isNotEmpty()) {
            binding.tvAuthorName.text = translations[0].author_name
            binding.tvText.text = translations[0].description
            binding.fabTranslationLeft.visibility = View.GONE

            if (translationsSize == 1) {
                binding.fabTranslationRight.visibility = View.GONE
                binding.fabTranslationLeft.visibility = View.GONE
            } else {
                var i = 0
                binding.fabTranslationRight.setOnClickListener {
                    if (i < translationsSize - 1) {
                        i++
                        binding.tvAuthorName.text = translations[i].author_name
                        binding.tvText.text = translations[i].description
                        binding.fabTranslationLeft.visibility = View.VISIBLE

                        if (i == translationsSize - 1) binding.fabTranslationRight.visibility =
                            View.GONE
                    }
                }


                binding.fabTranslationLeft.setOnClickListener {
                    if (i > 0) {
                        i--
                        binding.tvAuthorName.text = translations[i].author_name
                        binding.tvText.text = translations[i].description
                        binding.fabTranslationRight.visibility = View.VISIBLE

                        if (i == 0) binding.fabTranslationLeft.visibility = View.GONE
                    }
                }
            }
        }

        val commentary = verseItem.commentaries
        val commentarySize = commentary.size

        if (commentary.isNotEmpty()) {
            binding.tvAuthorNameCommentary.text = commentary[0].author_name
            binding.tvTextCommentary.text = commentary[0].description
            binding.fabCommentaryLeft.visibility = View.GONE

            if (commentarySize == 1) {
                binding.fabCommentaryRight.visibility = View.GONE
                binding.fabCommentaryLeft.visibility = View.GONE
            } else {
                var i = 0
                binding.fabCommentaryRight.setOnClickListener {
                    if (i < commentarySize - 1) {
                        i++
                        binding.tvAuthorNameCommentary.text = commentary[i].author_name
                        binding.tvTextCommentary.text = commentary[i].description
                        binding.fabCommentaryLeft.visibility = View.VISIBLE

                        if (i == commentarySize - 1) binding.fabCommentaryRight.visibility =
                            View.GONE
                    }
                }

                binding.fabCommentaryLeft.setOnClickListener {
                    if (i > 0) {
                        i--
                        binding.tvAuthorNameCommentary.text = commentary[i].author_name
                        binding.tvTextCommentary.text = commentary[i].description
                        binding.fabCommentaryRight.visibility = View.VISIBLE


                        if (i == 0) binding.fabCommentaryLeft.visibility = View.GONE
                    }
                }
            }
        }

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


}