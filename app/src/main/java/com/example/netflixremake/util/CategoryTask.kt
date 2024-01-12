package com.example.netflixremake.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.netflixremake.model.Category
import com.example.netflixremake.model.Movie
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class CategoryTask(private var callBack: CallBack) {

    interface CallBack{
        fun onPreExecute()
        fun onResult(categories: List<Category>)
        fun onFailure(message: String)
    }

    private val handler = Handler(Looper.getMainLooper())

    fun execute(urlText: String){
        //Executing UI thread
        callBack.onPreExecute()

        val executor = Executors.newSingleThreadExecutor()

        //Executing alternative thread
        executor.execute {
            var urlConnection: HttpsURLConnection? = null
            var stream: InputStream? = null
            var buffer: BufferedInputStream? = null

            try{
                urlConnection = URL(urlText).openConnection() as HttpsURLConnection //create, open and specify connection
                urlConnection.readTimeout = 4000 //4s to read all information
                urlConnection.connectTimeout = 4000 //4s to connect from server

                val statusCode: Int = urlConnection.responseCode
                if(statusCode > 400){
                    throw IOException("Server connection error!")
                }

                //Bytes sequence
                stream = urlConnection.inputStream

                /*Bytes to String

                - Form 1 (ready methods):
                    val jsonToString = stream.bufferedReader().use { it.readText() }

                - Form 2 (manually): */
                buffer = BufferedInputStream(stream)
                val jsonAsString = streamToString(buffer)
                var categories: List<Category> = stringToCategory(jsonAsString)
                handler.post { //Back to UI Thread
                    callBack.onResult(categories)
                }


            } catch (e: IOException){
                val message: String = e.message ?: "Unknown error!"
                Log.e("Error Test",message,e)
                handler.post {
                    callBack.onFailure(message)
                }

            } finally {
                urlConnection?.disconnect()
                stream?.close()
                buffer?.close()
            }
        }

    }

    private fun stringToCategory(text: String): List<Category>{
        var categories = mutableListOf<Category>()

        val jsonRoot = JSONObject(text)
        val jsonCategories = jsonRoot.getJSONArray("category")
        for(i in 0 until jsonCategories.length()){
            val jsonCategory = jsonCategories.getJSONObject(i)
            val categoryName = jsonCategory.getString("title")
            val categoryMovies: JSONArray = jsonCategory.getJSONArray("movie")

            var movies = mutableListOf<Movie>()
            for (j in 0 until categoryMovies.length()){
                val movie = categoryMovies.getJSONObject(j)
                val id = movie.getInt("id")
                val coverUrl = movie.getString("cover_url")
                movies.add(Movie(id, coverUrl))
            }
            categories.add(Category(categoryName,movies))
        }
        return categories
    }

    private fun streamToString(stream: InputStream): String{
        var bytes = ByteArray(1024)
        var outputStream = ByteArrayOutputStream()
        var read: Int
        do {
            read = stream.read(bytes)
            if(read>0) outputStream.write(bytes,0,read)
        } while(read>0)
        return String(outputStream.toByteArray())
    }
}