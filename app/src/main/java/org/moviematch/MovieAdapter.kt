package org.moviematch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

//class MovieAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
//
//    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val movieTitle: TextView = itemView.findViewById(R.id.movieTitleTextView)
//        private val moviePoster: ImageView = itemView.findViewById(R.id.moviePosterImageView)
//
//        fun bind(movie: Movie) {
//            movieTitle.text = movie.title
//            Glide.with(moviePoster.context)
//                .load(movie.posterPath)
//                .into(moviePoster)
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
//        return MovieViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
//        holder.bind(movies[position])
//    }
//
//    override fun getItemCount(): Int {
//        return movies.size
//    }
//}

class MovieAdapter(private var movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieTitleTextView: TextView = itemView.findViewById(R.id.movieTitleTextView)
        val movieOverviewTextView: TextView = itemView.findViewById(R.id.movieOverviewTextView)
        val moviePosterImageView: ImageView = itemView.findViewById(R.id.moviePosterImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentMovie = movies[position]
        holder.movieTitleTextView.text = currentMovie.title
        holder.movieOverviewTextView.text = currentMovie.overview

        val posterUrl = "https://image.tmdb.org/t/p/w500${currentMovie.posterPath}"
        Picasso.get().load(posterUrl).into(holder.moviePosterImageView)
    }

    override fun getItemCount() = movies.size

    fun setMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}