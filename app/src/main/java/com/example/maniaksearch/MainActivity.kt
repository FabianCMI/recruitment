package com.example.maniaksearch

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.example.maniaksearch.callapi.ISelectedCard
import com.example.maniaksearch.callapi.ItunesResFragment
import com.example.maniaksearch.filters.*
import org.json.JSONObject

const val TAG = "MainActivity"
const val SHARED_PREF_KEY = "last_query"

class MainActivity : AppCompatActivity(), ISelectedCountry, ISelectedLimit, ISelectedCard {

    // HashMap storing the query filters
    var queryParam: HashMap<String, String> = HashMap()
    var query: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)

        // Start filling query filters with default values
        queryParam["country"] = "FR"
        queryParam["explicit"] = "no"

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
        }
        filterRecyclerView.setHasFixedSize(true)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val searchView: SearchView = findViewById(R.id.searchBar)
        // Set a Listener on the Query submit that replace the current fragment with a new one
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryString: String): Boolean {
                query = queryString
                // Store the query in the shared preference to display it on next app usage
                sharedPref.edit() {
                    try {
                        remove(SHARED_PREF_KEY)
                        putString(SHARED_PREF_KEY, query + '_' + JSONObject(queryParam as Map<*, *>).toString())
                        commit()
                    } catch (e: Exception) {
                        Log.e(TAG, "Error storing query ${e.message}")
                    }
                }

                // Replace the fragment to update the view
                createFragApiRes(query, queryParam)
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
                query = it[0]
                queryParam = hashMapOf()
                val jsonObject = JSONObject(it[1])
                val keyIter = jsonObject.keys()
                while (keyIter.hasNext()) {
                    val key = keyIter.next()
                    queryParam[key] = jsonObject.get(key) as String
                }
                Log.d(TAG, "$query ${queryParam.toString()}")
                createFragApiRes(query, queryParam)
            }

        }
    }

    /****************** MENU *****************/

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
        when (item.itemId) {
            // (un)Allow explicit search
            R.id.param_explicit -> {
                item.isChecked = !item.isChecked
                if(item.isChecked) {
                    queryParam["explicit"] = "yes"
                } else {
                    queryParam["explicit"] = "no"
                }

                createFragApiRes(query, queryParam)
                return true
            }

            // Open Filters Dialog
            R.id.menu_filters -> {
                FiltersDialogFragment((queryParam["limit"]?.toInt()) ?:50).show(supportFragmentManager, "filters_menu")
            }
            // Open Language dialog
            R.id.param_lang -> {
                DialogFilterLangFragment(queryParam["country"]?: "FR").show(supportFragmentManager, "lang_dialog")
            }

            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    /**************** Other functions **************/

    private fun createFragApiRes(query: String, queryParam: HashMap<String, String>) {
        supportFragmentManager.commit {
            replace(
                    R.id.itunesResFragment,
                    ItunesResFragment.newInstance(query, queryParam),
                    ItunesResFragment::class.java.name
            )
        }
    }

    override fun onSelectedCountry(string: String?) {
        queryParam["country"] = when(string.toString()) {
            "France" -> "FR"
            "Allemagne" -> "DE"
            "Royaume-Uni" -> "GB"
            "USA" -> "US"
            else -> "FRA"
        }
        createFragApiRes(query, queryParam)
    }

    override fun onSelectedLimit(limit: Int?) {
        if (limit != null) {
            if(limit in 1..200) {
                queryParam["limit"] = limit.toString()
                createFragApiRes(query, queryParam)
            }
        }
    }

    override fun onSelectedCard(string: String?) {
       val intent = Intent(Intent.ACTION_VIEW, Uri.parse(string))
       try {
           this.startActivity(intent)
       } catch (e: Exception) {
           Log.e(TAG, "Error in implicit intent : ${e.message}")
       }
    }

}
