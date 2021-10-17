package com.apps.hrathi.a15puzzle.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.apps.hrathi.a15puzzle.R
import com.apps.hrathi.a15puzzle.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate view and obtain an instance of the binding class.
        val binding : MainFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.main_fragment, container, false);

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Assign the component to a property in the binding class.
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.fetchRandomImage()
    }

}