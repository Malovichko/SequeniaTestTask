package com.example.sequeniatesttask.data.preferences

import android.content.SharedPreferences
import com.example.sequeniatesttask.utils.PREF_KEY_GENER

class SharedPref(private val sharedPreferences: SharedPreferences){
    fun setGenre(genre: String?) {
        val editor = sharedPreferences.edit()
        editor?.putString(PREF_KEY_GENER, genre)
        editor?.apply()
    }

    fun getGenre(): String? {
        return sharedPreferences.getString(PREF_KEY_GENER, null)
    }
}