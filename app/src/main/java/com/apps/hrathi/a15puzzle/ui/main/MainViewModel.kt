package com.apps.hrathi.a15puzzle.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.hrathi.a15puzzle.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val imageRepository = ImageRepository()
    var randomImageUrl = MutableLiveData<String>()

    fun fetchRandomImage() {
        viewModelScope.launch(Dispatchers.IO) {
            val url = imageRepository.getImageUrl()
            randomImageUrl.postValue(url)
        }
    }
}