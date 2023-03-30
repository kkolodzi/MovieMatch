package org.moviematch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter

    private val apiKey = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.moviesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        movieAdapter = MovieAdapter(emptyList())
        recyclerView.adapter = movieAdapter

        // Fetch popular movies from TMDb API
        fetchPopularMovies()
    }

    private fun fetchPopularMovies() {
        GlobalScope.launch(Dispatchers.Main) {
            val url = "https://api.themoviedb.org/3/movie/popular?api_key=$apiKey"
            val client = OkHttpClient()

            val request = Request.Builder()
                .url(url)
                .build()

            withContext(Dispatchers.IO) {
                try {
                    val response = client.newCall(request).execute()
                    if (response.isSuccessful) {
                        val body = response.body()?.string()
                        val gson = GsonBuilder().create()
                        val popularMoviesResponse = gson.fromJson(body, PopularMoviesResponse::class.java)
                        withContext(Dispatchers.Main) {
                            movieAdapter.setMovies(popularMoviesResponse.results)
                        }
                    } else {
                        Log.e(TAG, "Request failed: ${response.code()} ${response.message()}")
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "Exception during request: ${e.message}")
                }
            }
        }
    }

    companion object {
        private const val TAG = "org.moviematch.MainActivity"
    }
}
