package com.arctouch.codechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieViewModel(private val api: TmdbApi, private val movieId: Int) : ViewModel() {

    private var movie = MutableLiveData<Movie>()

    init {
        loadMovieDetails()
    }

    fun getMovie() : LiveData<Movie> {
        return movie
    }

    private fun loadMovieDetails() {
        api.movie(movieId.toLong(), TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    movie.postValue(it.copy(genres = Cache.genres.filter { genre -> it.genreIds?.contains(genre.id) == true }))
                }
    }

}