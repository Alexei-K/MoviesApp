package com.kolis.movies_app.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
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
    val db = MovieRepositoryImpl()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.controller = Navigation.findNavController(view)
        db.getTrendingMovies(1).observe(viewLifecycleOwner, Observer {
            adapter.setModelsList(it as ArrayList<MovieModel>)
        })
        view.recipesRecycleView.adapter = adapter
        view.recipesRecycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_menu, menu)
        val search: SearchView = menu.findItem(R.id.search).actionView as SearchView
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let{ query ->
                    db.searchMovie(query).observe(viewLifecycleOwner, Observer {
                        adapter.setModelsList(it as ArrayList<MovieModel>)
                    })}
                return true
            }

        })
    }
}