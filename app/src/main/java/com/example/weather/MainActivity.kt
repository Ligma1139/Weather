package com.example.weather


import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.weather.ViewModel.MainViewModel
import com.example.weather.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var Get : SharedPreferences
    private lateinit var Set : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        Get = getSharedPreferences(packageName, MODE_PRIVATE)
        Set = Get.edit()

        viewModel = androidx.lifecycle.ViewModelProvider(this).get(MainViewModel::class.java)

        val cName = Get.getString("cityName","Delhi")
        binding.searchView.setText(cName)

        viewModel.refreshData(cName!!)

        getLiveData()

        val cityName = Get.getString("cityName",cName)
        binding.searchView.setText(cityName)
        viewModel.refreshData(cityName!!)

        binding.searchBtn.setOnClickListener {
            val cityName = binding.searchView.text.toString()
            Set.putString("cityName",cityName)
            Set.apply()
            getLiveData()
            viewModel.refreshData(cityName)
        }

    }

    private fun getLiveData() {
        viewModel.weather_data.observe(this,Observer{data->
            data?.let {
                binding.tempText.text = data.main.temp.toString() + "°C"
                binding.windData.text = data.wind.speed.toString() + "Km/h"
                binding.preasureData.text = data.main.pressure.toString()
                binding.feelsLikeValue.text = data.main.feels_like.toString() + "°C"
                binding.humidityData.text = data.main.humidity.toString() + "%"
                binding.CityName.text = data.sys.country.toString()

                Glide.with(this).load("https://openweathermap.org/img/wn/"+ data.weather.get(0).icon + "@2x.png")
                    .into(binding.weatherImage)
            }
        })
    }
}