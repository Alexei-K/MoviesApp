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
            isLikedIV.setImageDrawable(
                ResourcesCompat.getDrawable(
                    itemView.resources,
                    if (model.isLiked) R.drawable.like else R.drawable.dislike, null
                )
            )
            isLikedIV.setOnClickListener { v: View? ->
                model.isLiked = !model.isLiked
                isLikedIV.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        itemView.resources,
                        if (model.isLiked) R.drawable.like else R.drawable.dislike, null
                    )
                )
            }
            productNameTV.text = model.name
            if (model.newPrice >= model.oldPrice) {
                newPriceTV.text = "$ " + String.format(Locale.US, "%.2f", model.newPrice)
                timeRemainingTV.visibility = View.GONE
            } else {
                newPriceTV.text = "$ " + String.format(Locale.US, "%.2f", model.newPrice)
                if (model.timeTill > System.currentTimeMillis()) {
                    timeRemainingTV.text = getRemainingTimeText(
                        model.timeTill - System.currentTimeMillis(),
                        timeRemainingTV
                    )
                    timeRemainingTV.visibility = View.VISIBLE
                } else {
                    timeRemainingTV.visibility = View.GONE
                }
            }
            val votes = model.numberOfVotes
            numberOfVotesTV.text = "(" + votes.toInt() + ")"
            ratingBar.rating = model.getAvgMark()
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

    private fun getRemainingTimeText(timeInMillis: Long, v: View): String {
        val days = timeInMillis / (1000 * 60 * 60 * 24)
        val hours = (timeInMillis - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
        val minutes = (timeInMillis - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60)
        return if (days > 0) {
            v.resources.getQuantityString(
                R.plurals.time_remaining, days.toInt(),
                days.toInt(), hours.toInt(), minutes.toInt()
            )
        } else {
            //done due to limitations of plurals in English
            v.resources.getString(
                R.string.time_remain_zero_days,
                hours.toInt(), minutes.toInt()
            )
        }
    }
}