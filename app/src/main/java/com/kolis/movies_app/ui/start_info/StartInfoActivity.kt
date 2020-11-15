package com.kolis.movies_app.ui.start_info

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.kolis.movies_app.MainActivity
import com.kolis.movies_app.R
import com.kolis.movies_app.util.PrefConstants

class StartInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkRegistration()
        setContentView(R.layout.activity_register)
        val pager = findViewById<ViewPager>(R.id.register_viewPager)
        val tabs = findViewById<TabLayout>(R.id.register_tabs)
        setUpAdapter(pager)
        tabs.setupWithViewPager(pager)
    }

    private fun setUpAdapter(pager: ViewPager) {
        val adapter = InfoTabsAdapter(
            supportFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT
        )
        val firstFragment = StartInfoFragment()
        setFragmentBundle(
            firstFragment, R.drawable.registration_1,
            getString(R.string.welcome_to_fluxstore), getString(R.string.register_screen_text)
        )
        adapter.addFragment(0, firstFragment)
        val secondFragment = StartInfoFragment()
        setFragmentBundle(
            secondFragment,
            R.drawable.registration_2,
            getString(R.string.second_register_screen_title),
            getString(R.string.register_screen_text)
        )
        adapter.addFragment(1, secondFragment)
        val thirdFragment = StartInfoLastFragment()
        setFragmentBundle(
            thirdFragment, R.drawable.registration_3,
            "", getString(R.string.register_screen_text)
        )
        adapter.addFragment(2, thirdFragment)
        pager.adapter = adapter
    }

    private fun setFragmentBundle(
        fragment: StartInfoFragment,
        imgId: Int,
        title: String,
        text: String
    ) {
        val bundle = Bundle()
        bundle.putInt(StartInfoFragment.Companion.INFO_IMAGE, imgId)
        bundle.putString(StartInfoFragment.Companion.INFO_TITLE, title)
        bundle.putString(StartInfoFragment.Companion.INFO_TEXT, text)
        fragment.arguments = bundle
    }

    private fun checkRegistration() {
        val pref =
            PreferenceManager.getDefaultSharedPreferences(this)
        if (pref.getBoolean(PrefConstants.IS_LOGGED_PREF, false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}