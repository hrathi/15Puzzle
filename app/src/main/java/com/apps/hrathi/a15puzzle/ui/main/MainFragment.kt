package com.apps.hrathi.a15puzzle.ui.main

import android.app.ActionBar
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.apps.hrathi.a15puzzle.R
import com.apps.hrathi.a15puzzle.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding : MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate view and obtain an instance of the binding class.
        binding = DataBindingUtil.inflate(inflater,
            R.layout.main_fragment, container, false);

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Assign the component to a property in the binding class.
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            viewModel.fetchRandomImage()
        } catch (e: Exception) {
            binding.shuffle.text = "FAILED TO FETCH IMAGE"
        }

        binding.shuffle.setOnClickListener {
            val x = binding.fullImage.drawable as BitmapDrawable
            val displayMetrics = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)

            val width = displayMetrics.widthPixels
            val height = displayMetrics.heightPixels

            viewModel.shuffleImage(x.bitmap, width, height)

            binding.gridLayout.visibility = View.VISIBLE
            binding.fullImage.visibility = View.GONE
        }
    }


}