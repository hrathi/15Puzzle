package com.apps.hrathi.a15puzzle

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import org.json.JSONObject

class ImageRepository {
    fun getImageUrl(): String {
        val httpClient = OkHttpClient();
        val request = Request.Builder()
            .url("https://api.unsplash.com/photos/random")
            .addHeader("Accept-Version", "v1")
            .addHeader("Authorization", "Client-ID $ACCESS_KEY")
            .get()
            .build()

        val response = httpClient.newCall(request).execute()

        if (!response.isSuccessful) {
            throw Exception(response.message)
        }
        val jsonResponse: ResponseBody? = response.body
        jsonResponse?.let {
            val jsonObject = JSONObject(jsonResponse.string())
            return jsonObject.getJSONObject("urls").getString("regular")
        }

        return ""
    }

    companion object {
        private const val ACCESS_KEY: String = "Tvl1sNw2KmfrYR1upXHrabrE78ZoUnf30Dsp_W_q6Rg"
    }
}