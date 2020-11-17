package com.kolis.movies_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kolis.movies_app.R
import com.kolis.movies_app.data.MovieRepositoryImpl
import com.kolis.movies_app.data.dataModels.MovieModel
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private val adapter = MoviesListAdapter()
    private val pagaLoaded = 0
    private val maxPages = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.controller = Navigation.findNavController(view)
        val db = MovieRepositoryImpl()
        db.getTrendingMovies(1).observe(viewLifecycleOwner, Observer {
            adapter.setModelsList(it as ArrayList<MovieModel>)
        })
        view.recipesRecycleView.adapter = adapter
        view.recipesRecycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

        })
    }
}