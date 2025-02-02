package com.fps69.bhagavadgita.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fps69.bhagavadgita.databinding.ItemViewChaptersBinding
import com.fps69.bhagavadgita.modle.ChaptersItem
import com.fps69.bhagavadgita.viewmodel.MainViewModel

class AdapterChapters(
    val onChapterIVClicked: (ChaptersItem) -> Unit,
    val onFavoriteClicked: (ChaptersItem) -> Unit,
    val show: Boolean,
    val onFavoriteFilledClicked: (ChaptersItem) -> Unit,
    val viewModel: MainViewModel
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
                ivFavorite.visibility=View.GONE
                ivFavoriteFilled.visibility=View.VISIBLE
            }

            ivFavoriteFilled.setOnClickListener {
                onFavoriteFilledClicked(chapter)
                ivFavorite.visibility=View.VISIBLE
                ivFavoriteFilled.visibility=View.GONE
            }

            // This check is for jb ham SavedChapterFragment se verseFragment me move kre to
            if(!show){
                ivFavorite.visibility=View.GONE
                ivFavoriteFilled.visibility=View.GONE
            }
            else{

                // This check is for jb ham HomeFragment se verseFragment me move kre to kon kon sa chapter save hai hmare roomDB me (SharedPreference se check kr rhe hai )
                val keys = viewModel.getAllSavedChaptersFromSharedPreference()

                if(keys.contains(chapter.chapter_number.toString())){
                    ivFavorite.visibility=View.GONE
                    ivFavoriteFilled.visibility=View.VISIBLE
                }
                else{
                    ivFavorite.visibility=View.VISIBLE
                    ivFavoriteFilled.visibility=View.GONE
                }
            }
        }




    }


    class ChaptersViewHolder(val binding: ItemViewChaptersBinding): ViewHolder(binding.root) {

    }


}