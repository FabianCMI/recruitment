package com.example.maniaksearch.filters

import com.example.maniaksearch.R

class DataSource {
    fun loadFilters(): List<Filters> {
        return listOf<Filters>(
            Filters(R.string.media_all, R.drawable.ic_filter_all),
            Filters(R.string.media_music, R.drawable.ic_filter_music),
            Filters(R.string.media_music_video, R.drawable.ic_filter_music_video),
            Filters(R.string.media_movie, R.drawable.ic_filter_movie),
            Filters(R.string.media_short_movie, R.drawable.ic_filter_short_movie),
            Filters(R.string.media_tv_show, R.drawable.ic_filter_tv_show),
            Filters(R.string.media_audiobook, R.drawable.ic_filter_audiobook),
            Filters(R.string.media_ebook, R.drawable.ic_filter_ebook),
            Filters(R.string.media_software, R.drawable.ic_filter_software),
            Filters(R.string.media_podcast, R.drawable.ic_filter_podcasts)
        )
    }
}