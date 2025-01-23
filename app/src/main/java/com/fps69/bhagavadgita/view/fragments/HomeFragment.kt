package com.fps69.bhagavadgita.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fps69.bhagavadgita.NetworkManger
import com.fps69.bhagavadgita.R
import com.fps69.bhagavadgita.databinding.FragmentHomeBinding
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



        return binding.root
    }

    private fun checkNetworkConnection() {
        val networkManger = NetworkManger(requireContext())
        networkManger.observe(viewLifecycleOwner){connection ->
            if(connection == true){
                getAllChapters()
                binding.apply {
                    shimmer.visibility= View.VISIBLE
                    rvChapters.visibility= View.VISIBLE
                    tvShowingMessage.visibility= View.GONE
                }
            }
            else{
                binding.apply {
                    shimmer.visibility= View.GONE
                    rvChapters.visibility= View.GONE
                    tvShowingMessage.visibility= View.VISIBLE

                }
            }
        }

    }

    private fun getAllChapters() {

        lifecycleScope.launch {
            viewModel.getAllChapters().collect{ChapterList->
                setupRecyclerView(ChapterList)
            }
        }

    }

    private fun onChapterIVClicked(chapterItem : ChaptersItem){
        findNavController().navigate(R.id.action_homeFragment_to_versesFragment)

    }

    private fun setupRecyclerView(ChapterList: List<ChaptersItem>) {

        adapterChapters= AdapterChapters(::onChapterIVClicked)
        binding.rvChapters.adapter=adapterChapters
        adapterChapters.differ.submitList(ChapterList)
        binding.shimmer.visibility= View.GONE

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