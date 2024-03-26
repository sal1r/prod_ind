package com.example.lifestylehub.views.ui

import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.lifestylehub.R
import com.example.lifestylehub.databinding.ForecastBinding
import com.example.lifestylehub.interfaces.ForecastMVP
import com.example.lifestylehub.presenters.ForecastPresenter
import com.example.lifestylehub.utils.ImageLoader
import com.example.lifestylehub.utils.LocationObserver
import com.example.lifestylehub.utils.LocationUtiles
import kotlinx.coroutines.Runnable

class ForecastView @JvmOverloads constructor(
    context: android.content.Context,
    attrs: android.util.AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr), ForecastMVP.View {

    private var binding: ForecastBinding
    private val presenter: ForecastMVP.Presenter = ForecastPresenter(this)

    init {
        LayoutInflater.from(context).inflate(R.layout.forecast, this, true)
        binding = ForecastBinding.bind(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        LocationObserver.addListener {
            LocationUtiles.runWithLL(context, presenter::loadForecast,
                {
                    binding.tvLocationProblem.text = resources.getString(R.string.location_disabled)
                    binding.clLocationProblems.visibility = VISIBLE
                }, {
                    binding.tvLocationProblem.text = resources.getString(R.string.no_location_permission)
                    binding.clLocationProblems.visibility = VISIBLE
                }
            )
        }
    }

    override fun showForecast(forecast: ForecastMVP.Forecast) {
        binding.clLoader.visibility = VISIBLE

        fun formatTemp(temp: Int): String = if (temp > 0) "+$temp" else "$temp"
        val res = resources

        with(binding) {
            tvTemp.text = res.getString(R.string.temp, formatTemp(forecast.temp))
            tvTempFeels.text = res.getString(R.string.feels_temp, formatTemp(forecast.tempFeels))
            tvTempMin.text = res.getString(R.string.min_temp, formatTemp(forecast.minTemp))
            tvTempMax.text = res.getString(R.string.max_temp, formatTemp(forecast.maxTemp))
            tvCity.text = forecast.city
            tvStatus.text = forecast.status
            ImageLoader.to(ivIcon).load(forecast.icon)
        }

        binding.clLoader.visibility = GONE
    }

    override fun runInUiThread(action: Runnable) { post(action) }
}