package com.example.lifestylehub.presenters

import com.example.lifestylehub.interfaces.ForecastMVP
import com.example.lifestylehub.models.ForecastModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ForecastPresenter(private val view: ForecastMVP.View): ForecastMVP.Presenter {
    private val model: ForecastMVP.Model = ForecastModel()
    override fun loadForecast(lat: Double, lon: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            val forecast = model.getForecast(lat, lon)
            view.runInUiThread {
                view.showForecast(forecast)
            }
        }
    }
}