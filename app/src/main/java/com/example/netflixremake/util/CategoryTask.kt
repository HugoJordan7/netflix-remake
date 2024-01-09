package com.example.netflixremake.util

import android.util.Log
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

                //Bytes to String
                val jsonToString = stream.bufferedReader().use { it.readText() }
                Log.i("Test","Json to string")

            } catch (e: IOException){
                Log.e("Error Test",e.message ?: "Unknown error!",e)
            }
        }

    }
}