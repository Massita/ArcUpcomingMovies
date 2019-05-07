package com.arctouch.codechallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.api.TmdbApi

class MovieViewModelFactory(private val api: TmdbApi, private val movieId: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(api, movieId) as T
    }

}