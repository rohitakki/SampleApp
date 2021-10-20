package com.movie.app.preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.movie.app.model.repositories.Repository
import com.movie.app.preferences.PrefConstant.PREF_CACHE_EXPIRE
import com.movie.app.preferences.PrefConstant.PREF_TRENDING_REPOS

object AppPreferences {

    private const val NAME = "SAMPLE_REF"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val TRENDING_REPOS = Pair(PREF_TRENDING_REPOS, "")
    private val CACHE_EXPIRE_DATE = Pair(PREF_CACHE_EXPIRE, 0L)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(
            NAME,
            MODE
        )
        if (System.currentTimeMillis() > cacheExpireDate!!) {
            clearCachedData()
        }
    }

    private fun clearCachedData() {
        trendingRepos = ""
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    private var trendingRepos: String?
        get() = preferences.getString(TRENDING_REPOS.first, TRENDING_REPOS.second)
        set(value) = preferences.edit {
            it.putString(TRENDING_REPOS.first, value)
            it.commit()
        }

    private var cacheExpireDate: Long?
        get() = preferences.getLong(CACHE_EXPIRE_DATE.first, CACHE_EXPIRE_DATE.second)
        set(value) = preferences.edit {
            it.putLong(CACHE_EXPIRE_DATE.first, value!!)
            it.commit()
        }

    fun setTrendingRepos(repositories: List<Repository?>?) {
        val gson = Gson()
        val repoJson = gson.toJson(repositories)
        trendingRepos = repoJson
        setCacheDate()
    }

    private fun setCacheDate() {
        val cacheExpireTime: Long = System.currentTimeMillis() + (5*24*60*60*1000)
        cacheExpireDate = cacheExpireTime
    }

    fun getTrendingRepos(): List<Repository?>? {
        val gson = Gson()
        return gson.fromJson(trendingRepos, object : TypeToken<List<Repository?>?>() {}.type)
    }
}