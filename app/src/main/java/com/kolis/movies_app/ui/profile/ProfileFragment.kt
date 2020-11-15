package com.kolis.test_catalog_app.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.kolis.test_catalog_app.R
import com.kolis.test_catalog_app.util.PrefConstants
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        setLeaveProfile(root)
        return root
    }

    private fun setLeaveProfile(root: View) {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        if (pref.getBoolean(PrefConstants.IS_LOGGED_PREF, false)) {
            root.leaveProfile.setOnClickListener {
                viewModel.onLeaveProfile(this)
            }
            root.leaveProfile.visibility = View.VISIBLE
        }
    }
}