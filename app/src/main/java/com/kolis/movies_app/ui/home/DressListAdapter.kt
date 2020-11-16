package com.kolis.movies_app.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.kolis.movies_app.R
import com.kolis.movies_app.data.MovieModel
import com.kolis.movies_app.ui.home.DressListAdapter.DressViewHolder
import java.util.*

class DressListAdapter : RecyclerView.Adapter<DressViewHolder>() {
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
        var pictureIV: ImageView
        var isLikedIV: ImageView
        var productNameTV: TextView
        var newPriceTV: TextView
        var numberOfVotesTV: TextView
        var timeRemainingTV: TextView
        var ratingBar: RatingBar
        fun bind(model: MovieModel) {

            //заглушка. Фото не отправляется на сервер и не получается с сервера.
            pictureIV.setImageDrawable(
                ResourcesCompat.getDrawable(
                    itemView.resources,
                    model.getTestImageResource(), null
                )
            )

            productNameTV.text = model.title

            val votes = model.vote_count
            numberOfVotesTV.text = "(" + votes + ")"
            ratingBar.rating = model.vote_average
            itemView.setOnClickListener { v: View? ->
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationWatchDress(model)
                controller!!.navigate(action)
            }
        }

        init {
            pictureIV = itemView.findViewById(R.id.product_photo)
            isLikedIV = itemView.findViewById(R.id.likePhoto)
            productNameTV = itemView.findViewById(R.id.productName)
            newPriceTV = itemView.findViewById(R.id.priceActual)
            numberOfVotesTV = itemView.findViewById(R.id.numberOfMarks)
            timeRemainingTV = itemView.findViewById(R.id.timeRemaining)
            ratingBar = itemView.findViewById(R.id.rating)
        }
    }

}