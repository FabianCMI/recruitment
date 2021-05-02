package com.example.maniaksearch

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.example.maniaksearch.filters.DataSource
import com.example.maniaksearch.filters.FiltersAdapter
import com.example.maniaksearch.overview.OverviewFragment

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    // HashMap storing the query filters
    val queryParam: HashMap<String, String> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize data for filters recycler view
        val filtersDataset = DataSource().loadFilters()
        val filterRecyclerView = findViewById<RecyclerView>(R.id.filterRecyclerView)
        // call the adapter for the filter and ive
        filterRecyclerView.adapter = FiltersAdapter(this, filtersDataset) {
            queryParam["media"] = when(it.stringResourceId) {
                R.string.media_all -> "all"
                R.string.media_music -> "music"
                R.string.media_music_video -> "musicVideo"
                R.string.media_movie -> "movie"
                R.string.media_short_movie -> "shortFilm"
                R.string.media_audiobook -> "audiobook"
                R.string.media_podcast -> "podcast"
                R.string.media_tv_show -> "tvShow"
                R.string.media_ebook -> "ebook"
                R.string.media_software -> "software"
                else -> "all"
            }
            Toast.makeText(
                this,
                "${this.getString(R.string.media_toast)} ${this.getString(it.stringResourceId)}",
                Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Clicked on item ${it.toString()}")
        }
        filterRecyclerView.setHasFixedSize(true)


        // Start filling query filters with default values
        queryParam["country"] = "FR"
        queryParam["explicit"] = "no"

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val searchView: SearchView = findViewById(R.id.searchBar)
        // Set a Listener on the Query submit that replace the current fragment with a new one
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                supportFragmentManager.commit {
                    replace(
                        R.id.overviewFragment,
                        OverviewFragment.newInstance(query, queryParam),
                        OverviewFragment::class.java.name
                    )

                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    /**
     * Overriding function for using menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_layout, menu)
        return true
    }

    /**
     * Overriding function to use menu
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var countryISO = "FR"
        var isCountryChange = false
        when (item.itemId) {
            R.id.param_explicit -> {
                item.isChecked = !item.isChecked
                if(item.isChecked) {
                    queryParam["explicit"] = "yes"
                } else {
                    queryParam["explicit"] = "no"
                }
                Log.d("MainActivity", queryParam.toString())
                return true
            }
            R.id.param_lang_us -> {
                item.isChecked = !item.isChecked
                countryISO = "US"
                isCountryChange = true
            }
            R.id.param_lang_en -> {
                item.isChecked = !item.isChecked
                countryISO = "GB"
                isCountryChange = true
            }
            R.id.param_lang_fr -> {
                item.isChecked = !item.isChecked
                countryISO = "FR"
                isCountryChange = true
            }
            else -> super.onOptionsItemSelected(item)
        }
        queryParam["country"] = countryISO;
        // Send a toast is the country is changed
        if(isCountryChange) {
            Toast.makeText(
                this,
                "${this.getString(R.string.country_toast)} $countryISO",
                Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }

}
