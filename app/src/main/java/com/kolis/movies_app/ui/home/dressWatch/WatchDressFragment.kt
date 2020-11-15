package com.kolis.test_catalog_app.ui.home.dressWatch

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.kolis.test_catalog_app.R
import com.kolis.test_catalog_app.data.DressModel
import kotlinx.android.synthetic.main.fragment_watch_dress.*
import java.util.*

class WatchDressFragment : Fragment() {
    private lateinit var viewModel: WatchDressViewModel
    private val args: WatchDressFragmentArgs by navArgs()
    lateinit var dressModel: DressModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(WatchDressViewModel::class.java)
        dressModel = args.model
        return inflater.inflate(R.layout.fragment_watch_dress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillData()
        initListeners()
    }

    private fun fillData() {
        dressImage.setImageDrawable(
            ResourcesCompat.getDrawable(
                dressImage.resources,
                dressModel.getTestImageResource(), null
            )
        )
        productName.text = dressModel.name
        setUpDiscountPrice()
        numberOfMarks.text = "(" + dressModel.numberOfVotes.toInt() + ")"
        rating.rating = dressModel.getAvgMark()
        description.text = dressModel.description
        productCode.text = getString(R.string.product_code, dressModel.productCode)
        productCategory.text = getString(R.string.category, dressModel.category)
        productMaterial.text = getString(R.string.material, dressModel.material)
        productCountry.text = getString(R.string.country, dressModel.country)
        sizeSpinner.adapter = SpinnerAdapter(
            context, android.R.layout.simple_spinner_item,
            dressModel.sizes.toTypedArray()
        )
        colorSpinner.adapter = SpinnerAdapter(
            context, android.R.layout.simple_spinner_item,
            dressModel.colors.map { it.first }.toTypedArray()
        )

    }

    private fun setUpDiscountPrice() {
        if (dressModel.newPrice >= dressModel.oldPrice) {
            priceActual.text = getMoneyFormated(dressModel.newPrice)
            priceOld.visibility = View.INVISIBLE
        } else {
            priceActual.text = getMoneyFormated(dressModel.newPrice)
            priceOld.text = getMoneyFormated(dressModel.oldPrice)
            priceOld.paintFlags = priceOld.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            priceOld.visibility = View.VISIBLE
        }
    }

    private fun getMoneyFormated(amount: Float): String {
        return "$ " + String.format(
            Locale.US,
            "%.2f",
            amount
        )
    }

    private fun initListeners() {
        closeFragment.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}