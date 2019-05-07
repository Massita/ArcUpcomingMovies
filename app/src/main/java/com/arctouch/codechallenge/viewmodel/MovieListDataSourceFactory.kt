package com.arctouch.codechallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.data.MovieListDataSource
import com.arctouch.codechallenge.model.Movie

class MovieListDataSourceFactory(private val api: TmdbApi) : DataSource.Factory<Long, Movie>() {

    private var movieListLiveData = MutableLiveData<MovieListDataSource>()
    private lateinit var movieListDataSource: MovieListDataSource

    override fun create(): DataSource<Long, Movie> {
        movieListDataSource = MovieListDataSource(api)
        movieListLiveData.postValue(movieListDataSource)
        return movieListDataSource;
    }

}