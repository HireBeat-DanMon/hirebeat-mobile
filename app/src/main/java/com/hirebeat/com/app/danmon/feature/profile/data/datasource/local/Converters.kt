package com.hirebeat.com.app.danmon.feature.profile.data.datasource.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.CatalogItem
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.ProfileInstrument
import com.hirebeat.com.app.danmon.feature.profile.domain.entities.ProfileLink

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromInstrumentsList(value: List<ProfileInstrument>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toInstrumentsList(value: String): List<ProfileInstrument> {
        val type = object : TypeToken<List<ProfileInstrument>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromGenresList(value: List<CatalogItem>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toGenresList(value: String): List<CatalogItem> {
        val type = object : TypeToken<List<CatalogItem>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromLinksList(value: List<ProfileLink>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toLinksList(value: String): List<ProfileLink> {
        val type = object : TypeToken<List<ProfileLink>>() {}.type
        return gson.fromJson(value, type)
    }
}