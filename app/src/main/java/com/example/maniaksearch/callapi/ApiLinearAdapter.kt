package com.example.maniaksearch.callapi

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.maniaksearch.databinding.ItunesResItemBinding
import com.example.maniaksearch.network.AdaptedItunesRes
import com.example.maniaksearch.network.ApiResults
import java.text.NumberFormat
import java.util.*

class ApiLinearAdapter : ListAdapter<ApiResults,
        ApiLinearAdapter.ItunesResViewHolder>(DiffCallback) {

    class ItunesResViewHolder(private var binding: ItunesResItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Parsing the results to adapt displayed data in function of the media
         */
        fun bind(iTunesRes: ApiResults) {
            val adaptedItunesRes = AdaptedItunesRes()
            val country: Locale = when(iTunesRes.country) {
                "CHE" -> Locale.FRENCH
                "DEU" -> Locale.GERMANY
                "FRA" -> Locale.FRANCE
                "GBR" -> Locale.UK
                "USA" -> Locale.US
                else -> Locale.FRANCE
            }
            Log.d(TAG, "country locale $country")
            if (iTunesRes.trackPrice != null) {
                adaptedItunesRes.price =
                    NumberFormat.getCurrencyInstance(country).format(iTunesRes.trackPrice)
            } else if (iTunesRes.collectionPrice != null) {
                adaptedItunesRes.price =
                    NumberFormat.getCurrencyInstance().format(iTunesRes.collectionPrice)
            }
            if (iTunesRes.trackName != null) {
                adaptedItunesRes.name = iTunesRes.trackName
            } else if (iTunesRes.collectionPrice != null) {
                adaptedItunesRes.name =
                    iTunesRes.collectionName.toString()
                        .replace(Regex("""\((Una|A)bridged\)"""), "")
            }
            adaptedItunesRes.releaseDate = iTunesRes.releaseDate?.substring(0, 4) ?: ""
            adaptedItunesRes.artistName = iTunesRes.artistName
            adaptedItunesRes.artwork = iTunesRes.artworkUrl100

            binding.apiRes = adaptedItunesRes
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ApiLinearAdapter.ItunesResViewHolder {
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
