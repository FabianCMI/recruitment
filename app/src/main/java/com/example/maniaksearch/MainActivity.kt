package com.example.maniaksearch

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import com.example.maniaksearch.databinding.ActivityMainBinding
import com.example.maniaksearch.overview.OverviewFragment

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // HashMap storing the query filters
    val queryParam: HashMap<String, String> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
     * Overriding function for using menu
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var countryISO = "FR"
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
                Log.d(TAG, "US")
            }
            R.id.param_lang_en -> {
                item.isChecked = !item.isChecked
                countryISO = "GB"
                Log.d(TAG, "GB")
            }
            R.id.param_lang_fr -> {
                item.isChecked = !item.isChecked
                countryISO = "FR"
                Log.d(TAG, "FR")
            }
            else -> super.onOptionsItemSelected(item)
        }
        queryParam["country"] = countryISO;
        return true
    }

}
