package com.fps69.bhagavadgita.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel

import com.fps69.bhagavadgita.R
import com.fps69.bhagavadgita.databinding.FragmentVerseDetalisBinding
import com.fps69.bhagavadgita.databinding.FragmentVersesBinding
import com.fps69.bhagavadgita.viewmodel.MainViewModel


class VerseDetalisFragment : Fragment() {


    private lateinit var binding: FragmentVerseDetalisBinding
    private val mainViewModel : MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_verse_detalis, container, false)
    }


}