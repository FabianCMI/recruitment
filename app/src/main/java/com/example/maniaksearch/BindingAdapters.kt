package com.example.maniaksearch

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.maniaksearch.callapi.ApiCallStatus
import com.example.maniaksearch.callapi.ApiLinearAdapter
import com.example.maniaksearch.network.ApiResults

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
        imageView.load(imageUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("trackName")
fun bindName(textView: TextView, trackName: String?) {
    textView.text = trackName
}

@BindingAdapter("trackYear")
fun bindYear(textView: TextView, trackYear: String?) {
    textView.text = trackYear
}


@BindingAdapter("trackPrice")
fun bindPrice(textView: TextView, trackPrice: String?) {
    textView.text = trackPrice
}


@BindingAdapter("listApiRes")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ApiResults>?) {
    val adapter = recyclerView.adapter as ApiLinearAdapter
    adapter.submitList(data)
}

@BindingAdapter("apiCallStatus")
fun bindStatus(statusImageView: ImageView, status: ApiCallStatus?){
    when (status) {
        ApiCallStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ApiCallStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ApiCallStatus.SUCCESS -> statusImageView.visibility = View.GONE
    }
}