package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.database.NasaDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = NasaDatabase.getDatabase(application)
    private val repository = AsteroidRepository(database)

    private val _loading = MutableLiveData<Boolean>()
    private val _pictureOfDay = MutableLiveData<PictureOfDay?>()
    private val _navigateToDetail = MutableLiveData<Asteroid?>()

    val loading: LiveData<Boolean>
        get() = _loading
    val asteroids = repository.asteroids
    val pictureOfDay: LiveData<PictureOfDay?>
        get() = _pictureOfDay
    val navigateToDetail: LiveData<Asteroid?>
        get() = _navigateToDetail

    init {
        viewModelScope.launch {
            _loading.value = true
            refreshPictureOfDay()
            repository.refreshAsteroids()

            _loading.value = false
        }
    }

    private suspend fun refreshPictureOfDay() {
        try {
            val response = Network.asteroids.getPictureOfDay(BuildConfig.NASA_API_KEY)
            if (response.isSuccessful) {
                val image = response.body()!!
                if(image.mediaType == "image") {
                    _pictureOfDay.value = image
                }
            }
        } catch (e: Exception) {
            Log.e(MainViewModel::class.java.simpleName, e.message.toString())
        }
    }

    fun navigateToDetail(asteroid: Asteroid) {
        _navigateToDetail.value = asteroid
    }

    fun navigateToDetailDone() {
        _navigateToDetail.value = null
    }

    /**
     * Factory for constructing MainViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}