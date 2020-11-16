package com.kolis.movies_app.ui.home.movieInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.kolis.movies_app.MainActivity
import com.kolis.movies_app.R
import com.kolis.movies_app.data.dataModels.ExtraMovieModel
import com.kolis.movies_app.data.dataModels.MovieModel
import com.kolis.movies_app.util.PhotoUploader
import kotlinx.android.synthetic.main.fragment_movie_info.*

class MovieInfoFragment : Fragment() {
    private lateinit var viewModel: MovieInfoViewModel
    private val args: MovieInfoFragmentArgs by navArgs()
    lateinit var movieModel: MovieModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(MovieInfoViewModel::class.java)
        movieModel = args.model
        viewModel.getExtraMovieInfo(movieModel.id).observe(viewLifecycleOwner, Observer {
            fillExtraData(it)
        })
        (requireActivity() as MainActivity).supportActionBar?.hide()

        return inflater.inflate(R.layout.fragment_movie_info, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillData()
        initListeners()
    }

    private fun fillData() {
        PhotoUploader.uploadPhotoFromMoviesDb(movieModel.poster_path, moviePreview)
        movieName.text = movieModel.title
        description.text = movieModel.overview
        rating.rating = movieModel.vote_average
        numberOfMarks.text = "(" + movieModel.vote_count + ")"
    }

    private fun fillExtraData(model: ExtraMovieModel) {
        releaseDate.text = getString(R.string.release_date, model.release_date)
        directors.text = getString(R.string.directors, model.getPrettyDirectors())
        casts.text = getString(R.string.casts, model.getPrettyCasts())
        movieCategory.text = resources.getQuantityString(R.plurals.category, model.genres.size, model.getPrettyGenres())
    }

    private fun initListeners() {
        closeFragment.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as MainActivity).supportActionBar?.show()
    }
}