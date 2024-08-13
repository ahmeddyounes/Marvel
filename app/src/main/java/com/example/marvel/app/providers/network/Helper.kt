package com.example.marvel.app.providers.network

import com.example.marvel.app.MarvelApplication

class Helper {
    companion object {
        fun isNetworkAvailable(): Boolean {
            return MarvelApplication.instance.isNetworkAvailable()
        }

        fun convertToHttps(url: String): String {
            return if (url.startsWith("http://", ignoreCase = true)) {
                "https://" + url.substring(7)
            } else {
                url
            }
        }
    }
}