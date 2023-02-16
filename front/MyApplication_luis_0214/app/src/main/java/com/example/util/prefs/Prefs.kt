package com.example.util.prefs

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Prefs(context: Context) {
    private val prefNm="mPref"
    private val prefs=context.getSharedPreferences(prefNm,MODE_PRIVATE)

    var access:String?
        get() = prefs.getString("access",null)
        set(value){
            prefs.edit().putString("access",value).apply()
        }

    var refresh:String?
        get() = prefs.getString("refresh",null)
        set(value){
            prefs.edit().putString("refresh",value).apply()
        }

}