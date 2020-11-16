package com.kolis.movies_app.ui.home.dressWatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.kolis.movies_app.R
import com.kolis.movies_app.data.MovieModel
import kotlinx.android.synthetic.main.fragment_watch_dress.*

class WatchDressFragment : Fragment() {
    private lateinit var viewModel: WatchDressViewModel
    private val args: WatchDressFragmentArgs by navArgs()
    lateinit var movieModel: MovieModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(WatchDressViewModel::class.java)
        movieModel = args.model
        return inflater.inflate(R.layout.fragment_watch_dress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillData()
        initListeners()
    }

    private fun fillData() {
//        dressImage.setImageDrawable(
//            ResourcesCompat.getDrawable(
//                dressImage.resources,
//                movieModel.getTestImageResource(), null
//            )
//        )
//        productName.text = movieModel.name
//        setUpDiscountPrice()
//        numberOfMarks.text = "(" + movieModel.numberOfVotes.toInt() + ")"
//        rating.rating = movieModel.getAvgMark()
//        description.text = movieModel.description
//        productCode.text = getString(R.string.product_code, movieModel.productCode)
//        productCategory.text = getString(R.string.category, movieModel.category)
//        productMaterial.text = getString(R.string.material, movieModel.material)
//        productCountry.text = getString(R.string.country, movieModel.country)
//        sizeSpinner.adapter = SpinnerAdapter(
//            context, android.R.layout.simple_spinner_item,
//            movieModel.sizes.toTypedArray()
//        )
//        colorSpinner.adapter = SpinnerAdapter(
//            context, android.R.layout.simple_spinner_item,
//            movieModel.colors.map { it.first }.toTypedArray()
//        )

    }

    private fun setUpDiscountPrice() {
//        priceActual.text = movieModel.newPrice.toString()
    }

    private fun initListeners() {
        closeFragment.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}