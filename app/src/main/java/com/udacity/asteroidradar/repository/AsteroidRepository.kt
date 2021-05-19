package com.udacity.asteroidradar.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants.API_QUERY_DATE_FORMAT
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.asDatabaseModel
import com.udacity.asteroidradar.database.NasaDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class AsteroidRepository(private val database: NasaDatabase) {

    val asteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getAsteroids()) {
        it.asDomainModel()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val sdf = SimpleDateFormat(API_QUERY_DATE_FORMAT)
            var startDate = sdf.format(Date()) // Data de Hoje
            var endDate = "" // O Padrão da API é retornar os asteroids dos proximos 7 dias
1
            try {
                val response = Network.asteroids.getAsteroids(startDate, endDate, BuildConfig.NASA_API_KEY)
                val json = JSONObject(response.body().toString())
                val asteroids = parseAsteroidsJsonResult(json)

                database.asteroidDao.deletePastAsteroids()
                database.asteroidDao.insertAll(*asteroids.asDatabaseModel())
            } catch (e: Exception) {
                Log.e(AsteroidRepository::class.java.simpleName, e.message.toString())
            }
        }
    }
}