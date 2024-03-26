package com.example.lifestylehub.interfaces

import kotlinx.coroutines.Runnable

interface ForecastMVP {
    interface View {
        fun showForecast(forecast: ForecastMVP.Forecast)
        // метод для того, чтобы в презентере запустить действия, связанные с UI, в UI-потоке
        fun runInUiThread(action: Runnable)
    }

    interface Presenter {
        fun loadForecast(lat: Double, lon: Double)
    }

    interface Model {
        fun getForecast(lat: Double, lon: Double): ForecastMVP.Forecast
    }

    data class Forecast(
        val temp: Int = 0,
        val icon: String = "",
        val minTemp: Int = 0,
        val maxTemp: Int = 0,
        val tempFeels: Int = 0,
        val status: String = "",
        val city: String = ""
    )
}