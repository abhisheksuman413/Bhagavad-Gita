package com.fps69.bhagavadgita.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fps69.bhagavadgita.databinding.ItemViewVersesBinding
import com.fps69.bhagavadgita.datasource.room.SavedVerses

class AdapterSavedVerses(val onItemViewClicked: (Int, Int) -> Unit) : RecyclerView.Adapter<AdapterSavedVerses.SavedVersesViewHolder>() {


    val diffUtil = object : DiffUtil.ItemCallback<SavedVerses>(){

        override fun areItemsTheSame(oldItem: SavedVerses, newItem: SavedVerses): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SavedVerses, newItem: SavedVerses): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedVersesViewHolder {
        val binding = ItemViewVersesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SavedVersesViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SavedVersesViewHolder, position: Int) {
        val verses = differ.currentList[position]

        holder.binding.apply {
            tvVersesNumber.text="Vese ${verses.verse_number} of CH ${verses.chapter_number}"
            tvVerses.text = verses.translations[0].description

            ll.setOnClickListener {
                onItemViewClicked(verses.chapter_number, verses.verse_number)
            }
        }
    }

    class SavedVersesViewHolder( val binding: ItemViewVersesBinding):ViewHolder(binding.root) {

    }
}