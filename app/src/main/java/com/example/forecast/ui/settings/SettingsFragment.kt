package com.example.forecast.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.example.forecast.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateActionBarTitle("Settings")
        updateActionBarSubTitle(null)
    }

    private fun updateActionBarTitle(title : String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }
    private fun updateActionBarSubTitle(subTitle: String?){
        (activity as AppCompatActivity).supportActionBar?.subtitle = subTitle
    }
}