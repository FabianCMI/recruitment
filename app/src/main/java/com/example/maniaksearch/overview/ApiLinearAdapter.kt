package com.example.maniaksearch.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.maniaksearch.databinding.ItunesResItemBinding
import com.example.maniaksearch.network.ApiResults

class ApiLinearAdapter : ListAdapter<ApiResults,
        ApiLinearAdapter.ItunesResViewHolder>(DiffCallback) {

    class ItunesResViewHolder(private var binding: ItunesResItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(iTunesRes : ApiResults) {
            binding.apiRes = iTunesRes
            binding.executePendingBindings()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiLinearAdapter.ItunesResViewHolder {
        return ItunesResViewHolder(ItunesResItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ApiLinearAdapter.ItunesResViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    /**
     * Call back that check the differences between items and auto change the values if they are new
     */
    companion object DiffCallback : DiffUtil.ItemCallback<ApiResults>() {
        override fun areItemsTheSame(oldItem: ApiResults, newItem: ApiResults): Boolean {
            return oldItem.trackId == newItem.trackId
                    && oldItem.artistId == newItem.artistId
                    && oldItem.collectionId == newItem.collectionId
        }

        override fun areContentsTheSame(oldItem: ApiResults, newItem: ApiResults): Boolean {
            return oldItem.artistName == newItem.artistName
                    && oldItem.trackName == newItem.trackName
                    && oldItem.collectionName == newItem.collectionName
                    && oldItem.trackTimeMillis == newItem.trackTimeMillis
        }

    }
}
