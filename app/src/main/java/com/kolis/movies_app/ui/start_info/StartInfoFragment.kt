package com.kolis.movies_app.ui.start_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kolis.movies_app.R

open class StartInfoFragment : Fragment() {
    lateinit var viewModel: StartInfoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(StartInfoViewModel::class.java)
        viewModel.setUpInfo(
            requireArguments().getInt(INFO_IMAGE),
            requireArguments().getString(INFO_TITLE),
            requireArguments().getString(INFO_TEXT)
        )
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.info_fragment, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        setObservers(view)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setObservers(view: View) {
        viewModel.image
            .observe(viewLifecycleOwner, Observer { s: Int? ->
                val image =
                    view.findViewById<ImageView>(R.id.info_image)
                image.setImageDrawable(ResourcesCompat.getDrawable(view.resources, s!!, null))
            })
        viewModel.title.observe(
            viewLifecycleOwner,
            Observer { s: String? ->
                val mTitle = view.findViewById<TextView>(R.id.title_info)
                mTitle.text = s
            }
        )
        viewModel.text.observe(
            viewLifecycleOwner,
            Observer { s: String? ->
                val mText = view.findViewById<TextView>(R.id.text_info)
                mText.text = s
            }
        )
    }

    companion object {
        const val INFO_IMAGE = "info image"
        const val INFO_TITLE = "info title"
        const val INFO_TEXT = "info text"
    }
}