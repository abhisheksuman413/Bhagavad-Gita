package com.fps69.bhagavadgita.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fps69.bhagavadgita.databinding.ItemViewVersesBinding

class AdapterVerses(val onVersesItemClicked: (String, Int) -> Unit, val fromRoom: Boolean) : RecyclerView.Adapter<AdapterVerses.VersesViewHolder>() {


    val diffUtil = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {

            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VersesViewHolder {

        val binding = ItemViewVersesBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return VersesViewHolder(binding)
    }

    override fun getItemCount(): Int {

        return  differ.currentList.size
    }

    override fun onBindViewHolder(holder: VersesViewHolder, position: Int) {

        val verses = differ .currentList[position]
        holder.binding.apply {

            tvVersesNumber.text = "Verse ${position +1}"
            tvVerses.text = verses.toString()

            if(fromRoom == false){
                ll.setOnClickListener {
                    onVersesItemClicked(verses, position+1)
                }
            }
            else{
                ivNext.visibility= View.GONE
                ll.setOnClickListener {
                    Toast.makeText(holder.itemView.context,"This verse is not Saved \nSave this verse And check in Saved Verses ", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    class VersesViewHolder(val binding : ItemViewVersesBinding) : ViewHolder(binding.root) {

    }

}