package com.ekochkov.myweathers.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.ekochkov.myweathers.R
import com.ekochkov.myweathers.data.entity.Point
import com.ekochkov.myweathers.utils.Constants
import com.ekochkov.myweathers.view.fragment.*

const val TAG_TIME_PICKER_FRAGMENT  ="TAG_TIME_PICKER_FRAGMENT"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launchFragment(HomeFragment())
        val bundle = intent.extras?.getBundle(Constants.BUNDLE_KEY)

        bundle?.getParcelable<Point>(Constants.BUNDLE_KEY_POINT)?.let { point ->
            openPointPageFragment(point)
        }
    }

    private fun launchFragment(fragment: Fragment, tag: String?=null) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(tag)
            .commit()
    }

    fun openAddPointFragment() {
        launchFragment(AddPointFragment())
    }

    fun openSettingsFragment() {
        launchFragment(SettingsFragment())
    }

    fun openPointPageFragment(point: Point) {
        val bundle = Bundle()
        bundle.putParcelable(Constants.BUNDLE_KEY_POINT, point)
        val fragment = PointPageFragment()
        fragment.arguments = bundle
        launchFragment(fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                openSettingsFragment()
                true
            }
            else -> false
        }
    }
}