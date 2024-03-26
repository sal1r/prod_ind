package com.example.lifestylehub.models

import com.example.lifestylehub.api.ForecastApi
import com.example.lifestylehub.interfaces.ForecastMVP

class ForecastModel: ForecastMVP.Model {
    override fun getForecast(lat: Double, lon: Double): ForecastMVP.Forecast {
        return ForecastApi.getCurrentForecast(lat, lon)
    }
}