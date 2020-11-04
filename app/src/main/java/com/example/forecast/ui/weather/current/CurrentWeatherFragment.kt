package com.example.forecast.ui.weather.current

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.forecast.R
import com.example.forecast.internal.UnitSystem
import com.example.forecast.internal.glide.GlideApp
import com.example.forecast.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

@SuppressLint("SetTextI18n")
class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    private lateinit var viewModel: CurrentWeatherViewModel
    override val kodein by closestKodein()
    private val currentWeatherViewModelFactory by instance<CurrentWeatherViewModelFactory>()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, currentWeatherViewModelFactory)
                .get(CurrentWeatherViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        val weather = viewModel.weather.await()
        weather.observe(viewLifecycleOwner, Observer { currentWeather ->
            if (currentWeather == null) return@Observer
            group_loading.visibility = View.GONE
            updateActionBarLocation("Skikda")
            updateActionBarDate()
            updateTemperature(currentWeather.temperature, currentWeather.feelsLike)
            updateWeatherIcon(currentWeather.weatherIcons[0])
            updateWeatherCondition(currentWeather.weatherDescriptions)
            updateWind(currentWeather.windSpeed, currentWeather.windDir)
            updatePrecipitation(currentWeather.precip)
            updateVisibility(currentWeather.visibility)
        })
    }

    private fun chooseLocalizedUnitAbbreviation(metricUnit: String, scientificUnit: String, fahrenheitUnit: String): String {
        return when (viewModel.unitSystem) {
            UnitSystem.METRIC -> metricUnit
            UnitSystem.SCIENTIFIC -> scientificUnit
            UnitSystem.FAHRENHEIT -> fahrenheitUnit
        }
    }

    private fun updateActionBarLocation(location: String) {
        (activity as AppCompatActivity).supportActionBar?.title = location
    }

    private fun updateActionBarDate() {
        (activity as AppCompatActivity).supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperature(temperature: Double, feelsLike: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°K", "°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_feels_like_temperature.text = "Feels like $feelsLike$unitAbbreviation"
    }

    private fun updateWeatherCondition(weatherDescriptions: List<String>) {
        var descriptions = ""
        for (description in weatherDescriptions) {
            descriptions = StringBuilder().append(
                    "$description "
            ).toString()
        }
        textView_condition.text = descriptions
    }

    private fun updateWeatherIcon(weatherIcon: String) {
        GlideApp.with(this)//glide will cache images for us
                .load(R.drawable.ic_baseline_wb_sunny_24)
                .into(imageView_condition_icon)
    }

    private fun updateWind(windSpeed: Double, windDirection: String) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "kph", "mph")
        textView_wind.text = "Wind: $windDirection, $windSpeed $unitAbbreviation"
    }

    private fun updatePrecipitation(precipitation: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "mm", "in")
        textView_precipitation.text = "Precipitation: $precipitation $unitAbbreviation"
    }

    private fun updateVisibility(visibility: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "km", "mi")
        textView_visibility.text = "Visibility: $visibility $unitAbbreviation"
    }


}
