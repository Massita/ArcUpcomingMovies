package com.arctouch.codechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.model.Genre
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GenreViewModel(private val api: TmdbApi) : ViewModel() {

    init {
        loadGenres()
    }

    private val genres = MutableLiveData<List<Genre>>()

    fun getGenres() :LiveData<List<Genre>> {
        return genres
    }

    private fun loadGenres() {
        api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    genres.postValue(it.genres)
                }
    }

}