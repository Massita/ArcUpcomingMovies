package com.arctouch.codechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.model.Movie

class MovieListViewModel(private val api: TmdbApi) : ViewModel() {

    private var movieList: LiveData<PagedList<Movie>>
    private val movieListViewModelFactory = MovieListDataSourceFactory(api)

    init {
        val config = PagedList.Config.Builder()
                .setPageSize(10)
                .setInitialLoadSizeHint(10)
                .setEnablePlaceholders(false)
                .build()

        movieList = LivePagedListBuilder<Long, Movie>(movieListViewModelFactory, config).build()
    }

    fun getMovieList() : LiveData<PagedList<Movie>> {
        return movieList
    }

}