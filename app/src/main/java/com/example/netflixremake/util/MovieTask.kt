package com.example.netflixremake.util

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.netflixremake.model.Movie
import com.example.netflixremake.model.MovieDetail
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class MovieTask(private var callback: Callback) {

    interface Callback{
        fun onPreExecute()
        fun onResult(movieDetail: MovieDetail)
        fun onFailure(message: String)
    }

    private var handler = Handler(Looper.getMainLooper())
    private var executor = Executors.newSingleThreadExecutor()

    fun execute(urlText: String){

        callback.onPreExecute()

        executor.execute {
            var urlConnection: HttpsURLConnection? = null
            var stream: InputStream? = null
            var buffer: BufferedInputStream? = null

            try {
                urlConnection = URL(urlText).openConnection() as HttpsURLConnection
                urlConnection.connectTimeout = 2000
                urlConnection.readTimeout = 2000
                val statusCode = urlConnection.responseCode
                if(statusCode == 400){
                    stream = urlConnection.errorStream
                    buffer = BufferedInputStream(stream)
                    var jsonAsErrorString = streamToString(buffer)
                    Log.i("test",jsonAsErrorString)
                } else if (statusCode > 400) {
                    throw IOException("Server connection error!")
                }

                stream = urlConnection.inputStream
                buffer = BufferedInputStream(stream)
                var jsonAsString = streamToString(buffer)
                var movieDetail: MovieDetail = stringToMovieDetail(jsonAsString)
                handler.post {
                    callback.onResult(movieDetail)
                }

            } catch (e: IOException){
                handler.post {
                    callback.onFailure(e?.message ?: "Unknown error!")
                }
            } finally {
                urlConnection?.disconnect()
                stream?.close()
                buffer?.close()
            }
        }
    }

    private fun stringToMovieDetail(jsonText: String): MovieDetail{
        var jsonRoot = JSONObject(jsonText)
        val id = jsonRoot.getInt("id")
        val title = jsonRoot.getString("title")
        val desc = jsonRoot.getString("desc")
        val cast = jsonRoot.getString("cast")
        val coverUrl = jsonRoot.getString("cover_url")
        val jsonMovies = jsonRoot.getJSONArray("movie")

        var movies = mutableListOf<Movie>()
        for(i in 0 until jsonMovies.length()){
            val movie = jsonMovies.getJSONObject(i)
            val movieId = movie.getInt("id")
            val movieUrl = movie.getString("cover_url")
            movies.add(Movie(movieId,movieUrl))
        }

        var movieRoot = Movie(id,coverUrl,title,desc,cast)
        return MovieDetail(movieRoot,movies)
    }

    private fun streamToString(stream: InputStream): String{
        var byteArray = ByteArray(1024)
        var outputStream = ByteArrayOutputStream()
        var read: Int
        do{
            read = stream.read(byteArray)
            if(read>0) outputStream.write(byteArray,0,read)
        } while(read>0)
        return String(outputStream.toByteArray())
    }

}