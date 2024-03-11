package com.marbjorn.dummygoodsapp.utils

import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object ResponseUtils {
    fun rawData(spec: String): String {
        var output = ""
        var urlConnection: HttpURLConnection? = null
        try {
            val url = URL(spec)
            urlConnection = url.openConnection() as HttpURLConnection
            val inputStream = urlConnection.inputStream
            val inputStreamReader = InputStreamReader(inputStream)
            var data = inputStreamReader.read()
            while (data != -1) {
                output += data.toChar()
                data = inputStreamReader.read()
            }
            return output
        } finally {
            urlConnection?.disconnect()
        }
    }
}
