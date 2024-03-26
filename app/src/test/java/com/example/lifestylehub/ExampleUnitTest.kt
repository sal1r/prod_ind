package com.example.lifestylehub

import com.example.lifestylehub.interfaces.ForecastMVP
import kotlinx.coroutines.Runnable
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

class ForecastUntiTest {
    data class ForecastWidget(
        val temp: String = "",
        val icon: String = "",
        val minTemp: String = "",
        val maxTemp: String = "",
        val tempFeels: String = "",
        val status: String = "",
        val city: String = ""
    )

    var forecastWidget = ForecastWidget()

    class MyView: ForecastMVP.View {
        override fun showForecast(forecast: ForecastMVP.Forecast) {

        }

        override fun runInUiThread(action: Runnable) {
            TODO("Not yet implemented")
        }
    }

    class MyPresenter: ForecastMVP.Presenter {
        override fun loadForecast(lat: Double, lon: Double) {
            TODO("Not yet implemented")
        }
    }

    class MyModel: ForecastMVP.Model {
        override fun getForecast(lat: Double, lon: Double): ForecastMVP.Forecast {
            TODO("Not yet implemented")
        }
    }

    @Test
    fun gettingDataIsCorrect() {
        assertEquals("bimbimbambam", "bimbimbambam")
    }

    @Test
    fun displayingDataIsCorrect() {
        assertEquals("fizzbuzz", "fizzbuzz")
    }
}