package com.example.maniaksearch.network

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
    val collectionPrice: Double? = null,
    val trackPrice: Double? = null,
    val releaseDate: String? = null,
    val trackTimeMillis: Int?=null,
    val currency: String? = null,
    val shortDescription: String? = null,
    val primaryGenreName: String? = null
)