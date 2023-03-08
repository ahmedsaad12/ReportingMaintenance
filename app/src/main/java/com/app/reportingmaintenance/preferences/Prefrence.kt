package com.app.reportingmaintenance.preferences

import android.content.Context
import android.content.SharedPreferences

import com.app.reportingmaintenance.model.UserModel

import com.app.reportingmaintenance.tags.Tags
import com.google.gson.Gson


class Preferences private constructor() {


    fun create_update_userData(context: Context, userModel: UserModel?) {
        val preferences: SharedPreferences =
            context.getSharedPreferences("userPref", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val gson = Gson()
        val userDataGson: String = gson.toJson(userModel)
        editor.putString("user_data", userDataGson)
        editor.apply()
        createSession(context, Tags.session_login)
    }

    fun getUserData(context: Context): UserModel {
        val preferences: SharedPreferences =
            context.getSharedPreferences("userPref", Context.MODE_PRIVATE)
        val userDataGson = preferences.getString("user_data", "")
        return Gson().fromJson(userDataGson, UserModel::class.java)
    }

    fun createSession(context: Context, session: String?) {
        val preferences: SharedPreferences =
            context.getSharedPreferences("sessionPref", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("session", session)
        editor.apply()
    }

    fun getSession(context: Context): String? {
        val preferences: SharedPreferences =
            context.getSharedPreferences("sessionPref", Context.MODE_PRIVATE)
        return preferences.getString("session", "")
    }

    fun clear(context: Context) {
        val preferences1: SharedPreferences =
            context.getSharedPreferences("userPref", Context.MODE_PRIVATE)
        val editor1 = preferences1.edit()
        editor1.clear()
        editor1.apply()
        val preferences2: SharedPreferences =
            context.getSharedPreferences("sessionPref", Context.MODE_PRIVATE)
        val editor2 = preferences2.edit()
        editor2.clear()
        editor2.apply()
    }

    companion object {
        private var instance: Preferences? = null
        @Synchronized
        fun newInstance(): Preferences? {
            if (instance == null) {
                instance = Preferences()
            }
            return instance
        }
    }
}