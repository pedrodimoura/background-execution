package com.github.pedrodimoura.backgroundexecution.util

object UrlUtil {

    private const val FIRST_PAGE = "1"

    fun getNextPageFromStringUrl(url: String): String {
        val result = url.splitToSequence("=").last()
        return if (result.isEmpty()) FIRST_PAGE else result
    }

}