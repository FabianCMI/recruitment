package com.example.maniaksearch.filters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.maniaksearch.R

class FiltersAdapter constructor(
    private val context: Context,
    private val dataset: List<Filters>,
    val clickListener: (Filters) -> Unit) :
    RecyclerView.Adapter<FiltersAdapter.FilterItemViewHolder>() {

    class FilterItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.filterText)
        val imageView: ImageView = view.findViewById(R.id.filterImgView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.filter_item, parent, false)
        val filterItemViewHolder =
        return FilterItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: FilterItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text =  context.resources.getString(item.stringResourceId)
        holder.imageView.setImageResource(item.drawableResourceId)

        holder.itemView.setOnClickListener { clickListener(item) }
    }

    override fun getItemCount(): Int = dataset.size

}