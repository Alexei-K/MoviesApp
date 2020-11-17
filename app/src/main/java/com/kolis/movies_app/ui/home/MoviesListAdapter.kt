package com.kolis.movies_app.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.kolis.movies_app.R
import com.kolis.movies_app.data.dataModels.MovieModel
import com.kolis.movies_app.ui.home.MoviesListAdapter.DressViewHolder
import com.kolis.movies_app.util.PhotoUploader
import java.util.*

class MoviesListAdapter : RecyclerView.Adapter<DressViewHolder>() {
    private var dressList = ArrayList<MovieModel>()
    var controller: NavController? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DressViewHolder {
        return DressViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movie_item_view, parent, false))
    }

    override fun onBindViewHolder(holder: DressViewHolder, position: Int) {
        holder.bind(dressList[position])
    }

    override fun getItemCount(): Int {
        return dressList.size
    }

    fun setModelsList(modelList: ArrayList<MovieModel>) {
        dressList = modelList
        notifyDataSetChanged()
    }

    inner class DressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var moviePreview: ImageView
        var productNameTV: TextView
        var description: TextView
        var numberOfVotesTV: TextView
        var ratingBar: RatingBar
        fun bind(model: MovieModel) {

            model.poster_path?.let{PhotoUploader.uploadPhotoFromMoviesDb(it, moviePreview)}

            productNameTV.text = model.title
            description.text = model.overview
            val votes = model.vote_count
            numberOfVotesTV.text = "(" + votes + ")"
            ratingBar.rating = model.vote_average
            itemView.setOnClickListener { v: View? ->
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationWatchDress(model)
                controller!!.navigate(action)
            }
        }

        init {
            moviePreview = itemView.findViewById(R.id.movie_poster)
            productNameTV = itemView.findViewById(R.id.movieName)
            description = itemView.findViewById(R.id.movieDescription)
            numberOfVotesTV = itemView.findViewById(R.id.numberOfMarks)
            ratingBar = itemView.findViewById(R.id.rating)
        }
    }

}