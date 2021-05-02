package com.example.maniaksearch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import com.example.maniaksearch.databinding.ActivityMainBinding
import com.example.maniaksearch.overview.OverviewFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val searchView: SearchView = findViewById(R.id.searchBar)
        // Set a Listener on the Query submit that replace the current fragment with a the new one
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                supportFragmentManager.commit {
                    Log.d("OverView", "replace frag $query")
                    replace(R.id.overviewFragment, OverviewFragment.newInstance(query), OverviewFragment::class.java.name)
                }
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }
}