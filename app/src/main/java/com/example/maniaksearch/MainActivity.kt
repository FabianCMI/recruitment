package com.example.maniaksearch

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.example.maniaksearch.filters.DataSource
import com.example.maniaksearch.filters.FiltersAdapter
import com.example.maniaksearch.overview.OverviewFragment
import org.json.JSONObject

const val TAG = "MainActivity"
const val SHARED_PREF_KEY = "last_query"

class MainActivity : AppCompatActivity() {

    // HashMap storing the query filters
    val queryParam: HashMap<String, String> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)



        // Initialize data for filters recycler view
        val filtersDataset = DataSource().loadFilters()
        val filterRecyclerView = findViewById<RecyclerView>(R.id.filterRecyclerView)


        // call the adapter for the filter
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
                // Store the query in the shared preference to display it on next app usage
                sharedPref.edit() {
                    try {
                        remove(SHARED_PREF_KEY)
                        putString(SHARED_PREF_KEY, query + '_' + JSONObject(queryParam as Map<*, *>).toString())
                        commit()
                        Log.d(TAG, "Store ${query + '_' + JSONObject(queryParam as Map<*, *>).toString()}")
                    } catch (e: Exception) {
                        Log.e(TAG, "Error storing query $e")
                    }
                }

                // Replace the fragment to update the view
                createFrag(query, queryParam)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        // Display at the app creation the last itunes search of the user stored in sharePreferences
        val lastQuery = sharedPref.getString(SHARED_PREF_KEY, "");
        if(lastQuery != "") {
            // Retrieve the data stored (query_jsonStringOf(queryParam)) then create a frag with it
            val queryData = lastQuery?.split("_")
            queryData?.let {
                val query = it[0]
                val queryParam: HashMap<String, String> = hashMapOf()
                val jsonObject = JSONObject(it[1])
                val keyIter = jsonObject.keys()
                while (keyIter.hasNext()) {
                    val key = keyIter.next()
                    queryParam[key] = jsonObject.get(key) as String
                }
                createFrag(query, queryParam)
            }

        }
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

    private fun createFrag(query: String, queryParam: HashMap<String, String>) {
        supportFragmentManager.commit {
            replace(
                    R.id.overviewFragment,
                    OverviewFragment.newInstance(query, queryParam),
                    OverviewFragment::class.java.name
            )
        }
    }

}
