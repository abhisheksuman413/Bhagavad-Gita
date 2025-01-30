package com.fps69.bhagavadgita.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fps69.bhagavadgita.databinding.ItemViewChaptersBinding
import com.fps69.bhagavadgita.modle.ChaptersItem

class AdapterChapters(
    val onChapterIVClicked: (ChaptersItem) -> Unit,
    val onFavoriteClicked: (ChaptersItem) -> Unit
) : RecyclerView.Adapter<AdapterChapters.ChaptersViewHolder>(){


    val diffUtil = object:DiffUtil.ItemCallback<ChaptersItem>(){
        override fun areItemsTheSame(oldItem: ChaptersItem, newItem: ChaptersItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChaptersItem, newItem: ChaptersItem): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChaptersViewHolder {


        val binding = ItemViewChaptersBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ChaptersViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ChaptersViewHolder, position: Int) {

        val chapter = differ.currentList[position]
        holder.binding.apply {
            tvChapterNumber.text = "Chapter ${chapter.chapter_number}"
            tvChaptersTitle.text= chapter.name_translated
            tvChaptersDescription.text= chapter.chapter_summary
            tvVersesCount.text= chapter.verses_count.toString()

            ll.setOnClickListener{
                onChapterIVClicked(chapter)
            }

            ivFavorite.setOnClickListener{
                onFavoriteClicked(chapter)
            }
        }




    }


    class ChaptersViewHolder(val binding: ItemViewChaptersBinding): ViewHolder(binding.root) {

    }


}