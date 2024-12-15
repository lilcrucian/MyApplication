package com.example.myapplication

import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import java.io.IOException

object CarInfoFetcher {
    private val client = OkHttpClient()

    fun fetchCarInfo(carNumber: String): String {
        val url = "https://avtocod.ru/$carNumber"
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val document = Jsoup.parse(response.body?.string())

            return document.title() //
        }
    }
}
