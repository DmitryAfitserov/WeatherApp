package com.app.weatherapp


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.app.weatherapp.repository.Repo
import com.app.weatherapp.view.AdapterViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val tabTitles = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2,
        R.string.tab_text_3,
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewPager.adapter = AdapterViewPager(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = applicationContext.resources.getString(tabTitles[position])

            viewPager.setCurrentItem(tab.position, true)
        }.attach()













    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
              //  newGame()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        Log.d("EEE", "destroyBD()")
        Repo.getBD(applicationContext).destroyBD()
        super.onDestroy()

    }
}