package com.pabloarista.serverdrivenuidemo.data.repositories

import com.google.gson.Gson
import com.pabloarista.serverdrivenuidemo.data.models.Component
import okhttp3.OkHttpClient
import okhttp3.Request

class ServerDrivenUIRepository {
    fun getServerDrivenUi(apiPath: String): Component? {
        try {
            //build the http request
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("http://192.168.0.201:5001/$apiPath")
                .build()

            val response = client.newCall(request).execute()
            val rootComponent: Component?
            response.use {
                val json = it.body?.string()
                rootComponent = if (json != null) Gson().fromJson(json, Component::class.java)
                else null
            }//use

            return rootComponent
        } catch(e: Throwable) {
            return null
        }
    }
}