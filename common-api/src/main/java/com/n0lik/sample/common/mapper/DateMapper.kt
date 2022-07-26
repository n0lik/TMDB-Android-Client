package com.n0lik.sample.common.mapper

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class DateMapper
@Inject constructor() {

    private val releaseDateFormatter by lazy {
        SimpleDateFormat("yyyy", Locale.getDefault())
    }
    private val dateFormatter by lazy {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    fun mapToReleaseDate(dateString: String?): String? {
        dateString ?: return null
        return try {
            val date = dateFormatter.parse(dateString)
            date?.let { releaseDateFormatter.format(it) }
        } catch (e: ParseException) {
            null
        }
    }
}