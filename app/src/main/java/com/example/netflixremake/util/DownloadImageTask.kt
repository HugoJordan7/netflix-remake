package com.example.netflixremake.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class DownloadImageTask(private var callBack: CallBack) {

    interface CallBack{
        fun onResult(bitmap: Bitmap)
    }

    private val handler = Handler(Looper.getMainLooper())
    private val executor = Executors.newSingleThreadExecutor()

    fun execute(urlText: String){

        executor.execute {
            var urlConnection: HttpsURLConnection? = null
            var stream: InputStream? = null

            try {
                urlConnection = URL(urlText).openConnection() as HttpsURLConnection
                urlConnection.readTimeout = 2000
                urlConnection.connectTimeout = 2000

                if(urlConnection.responseCode > 400){
                    throw IOException("Server connection error!")
                }

                stream = urlConnection.inputStream

                val bitmap = BitmapFactory.decodeStream(stream)
                handler.post {
                    callBack.onResult(bitmap)
                }

            } catch (e: IOException){
                val message: String = e.message ?: "Unknown error!"
                Log.e("Error Test",message,e)
            } finally {
                stream?.close()
                urlConnection?.disconnect()
            }
        }


    }

}