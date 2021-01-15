package com.app.weatherapp.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.app.weatherapp.view.MainActivity
import com.app.weatherapp.R


class CustomPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

        if (key == context?.resources?.getString(R.string.key_auto_update)) {
            if (sharedPreferences?.getBoolean(key, false)!!) {

                (activity as MainActivity).startWorker(
                    sharedPreferences
                        .getString(
                            context?.resources?.getString(R.string.key_interval),
                            "4"
                        )!!.toLong()
                )

            } else {
                (activity as MainActivity).stopWorker()
            }
        }
        if (key == context?.resources?.getString(R.string.key_interval)) {
            if (sharedPreferences?.getBoolean(
                    context?.resources?.getString(R.string.key_auto_update), false
                )!!
            ) {

                (activity as MainActivity)
                    .updateWorker(sharedPreferences.getString(key, "4")!!.toLong())
            }


        }
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }


}