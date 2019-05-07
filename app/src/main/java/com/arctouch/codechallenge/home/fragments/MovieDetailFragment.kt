package com.arctouch.codechallenge.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.arctouch.codechallenge.viewmodel.MovieViewModel
import com.arctouch.codechallenge.viewmodel.MovieViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_detail_fragment.*

class MovieDetailFragment : Fragment() {

    private val args: MovieDetailFragmentArgs by navArgs()
    private lateinit var movieViewModel: MovieViewModel
    private val movieImageUrlBuilder = MovieImageUrlBuilder()

    private lateinit var movieViewModelFactory : MovieViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadFactories()
        loadViewModels()
    }

    private fun loadFactories() {
        movieViewModelFactory = MovieViewModelFactory(TmdbApi.create(), args.movieId)
    }

    private fun loadViewModels() {
        movieViewModel = ViewModelProviders.of(this, movieViewModelFactory).get(MovieViewModel::class.java)
        movieViewModel.getMovie().observe(this, Observer { setMovieInfo(it) })
    }

    private fun setMovieInfo(movie: Movie) {
        movieTitle.text = movie.title
        Glide.with(this)
                .load(movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(moviePoster)
        Glide.with(this)
                .load(movie.backdropPath?.let { movieImageUrlBuilder.buildBackdropUrl(it) })
                .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                .into(movieBackdropImage)
        movieOverview.text = movie.overview
        movieReleaseDate.text = movie.releaseDate
        movieGenres.text = movie.genres?.joinToString(separator = ", ") { it.name }
    }

}