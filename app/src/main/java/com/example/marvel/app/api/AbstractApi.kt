package com.example.marvel.app.api

import java.security.MessageDigest

abstract class AbstractApi(val config: MarvelApiConfig) {
    fun getTimestamp(): String {
        return System.currentTimeMillis().toString()
    }

    fun getApiKey(timestamp: String): String {
        val privateKey = config.privateKey
        val publicKey = config.publicKey
        val input = timestamp + privateKey + publicKey

        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(input.toByteArray())

        return digest.joinToString("") { "%02x".format(it) }
    }
}