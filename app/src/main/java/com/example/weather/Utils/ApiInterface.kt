package com.example.weather.Utils


import com.example.weather.Model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("data/2.5/weather?&units=metric&appid=b97b9ed1a114470500a2417a182946fb")
    fun getData(
        @Query("q") cityName: String
    ): Single<WeatherModel>
}