package com.example.maniaksearch.network

import com.example.maniaksearch.R

/**
 * Data class for parsing interesting api results from the Json to an object with moshi
 */
data class ApiListResults(
    val resultCount: Int, val results: List<ApiResults>
)

data class ApiResults(
    val kind: String? = null,
    val artistId: Int? = null,
    val collectionId: Int? = null,
    val trackId: Int? = null,
    val artistName: String? = null,
    val collectionName: String? = null,
    val trackName: String? = null,
    var collectionViewUrl: String? = null,
    val artworkUrl100: String? = null,
    val collectionPrice: Double? = null,
    val trackPrice: Double? = null,
    val releaseDate: String? = null,
    val trackTimeMillis: Int? = null,
    val country: String = "FRA",
    val currency: String? = null,
    val shortDescription: String? = null,
    val primaryGenreName: String? = null
)

data class AdaptedItunesRes(
    var name: String? = "Oeuvre inconnue",
    var artistName: String? = "Artist inconnu",
    var price: String? = "",
    var artwork: String? = R.drawable.ic_broken_image.toString(),
    var releaseDate: String? = "",
    var collectionViewUrl: String? = ""
)