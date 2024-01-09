package com.example.netflixremake.util

import android.util.Log
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class CategoryTask {

    fun execute(url: String){
        //Executing UI thread
        val executor = Executors.newSingleThreadExecutor()

        //Executing alternative thread
        executor.execute {
            try{
                val requestUrl = URL(url)
                val urlConnection = requestUrl.openConnection() as HttpsURLConnection
                urlConnection.readTimeout = 2000 //2s to read all information
                urlConnection.connectTimeout = 2000 //2s to connect from server

                val statusCode: Int = urlConnection.responseCode
                if(statusCode > 400){
                    throw IOException("Server connection error!")
                }

                //Bytes sequence
                val stream: InputStream = urlConnection.inputStream

                /*Bytes to String

                - Form 1 (ready methods):
                    val jsonToString = stream.bufferedReader().use { it.readText() }

                - Form 2 (manually): */
                val buffer = BufferedInputStream(stream)
                val jsonToString = streamToString(buffer)
                Log.i("Test json",jsonToString)

            } catch (e: IOException){
                Log.e("Error Test",e.message ?: "Unknown error!",e)
            }
        }

    }

    private fun streamToString(stream: InputStream): String{
        var bytes = ByteArray(1024)
        var outputStream = ByteArrayOutputStream()
        var read: Int
        do {
            read = stream.read(bytes)
            outputStream.write(bytes,0,read)
        } while (read <= 0)
        return String(outputStream.toByteArray())
    }
}