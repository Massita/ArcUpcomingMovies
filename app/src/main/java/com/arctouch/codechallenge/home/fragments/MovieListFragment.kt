package com.arctouch.codechallenge.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.home.adapters.HomeAdapter
import com.arctouch.codechallenge.viewmodel.MovieListViewModel
import com.arctouch.codechallenge.viewmodel.MovieListViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.movie_list_fragment.*

class MovieListFragment : Fragment() {
    private lateinit var moviesViewModel: MovieListViewModel
    private lateinit var moviesViewModelFactory: MovieListViewModelFactory
    private lateinit var moviesAdapter: HomeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        loadFactories()
        loadViewModels()
    }

    private fun prepareRecyclerView() {
        moviesAdapter = HomeAdapter()
        recyclerView.apply {
            adapter = moviesAdapter
        }
    }

    private fun loadFactories() {
        moviesViewModelFactory = MovieListViewModelFactory(TmdbApi.create())
    }

    private fun loadViewModels() {
        moviesViewModel = ViewModelProviders.of(this, moviesViewModelFactory).get(MovieListViewModel::class.java)
        moviesViewModel.getMovieList().observe(this, Observer {
            progressBar.visibility = View.INVISIBLE
            moviesAdapter.submitList(it)
        })
    }

}