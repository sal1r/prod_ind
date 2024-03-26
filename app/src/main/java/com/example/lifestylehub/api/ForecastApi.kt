package com.example.lifestylehub.api

import com.example.lifestylehub.interfaces.ForecastMVP

object ForecastApi {
    private const val API_KEY = "a2f1fa8feb93d4e027eae0210c619777"
    private val client = okhttp3.OkHttpClient()
    fun getCurrentForecast(lat: Double, lon: Double): ForecastMVP.Forecast {
        val request = okhttp3.Request.Builder()
            .url("https://api.openweathermap.org/data/2.5/weather?&units=metric&lang=ru" +
                    "&lat=${lat}" +
                    "&lon=${lon}" +
                    "&appid=${API_KEY}")
            .build()

        try {
            val response = client.newCall(request).execute()
            val body = response.body?.string()

            if (!response.isSuccessful || body == null) throw RuntimeException("Failed to load data")

            val json = org.json.JSONObject(body)
            val main = json.getJSONObject("main")
            val weather = json.getJSONArray("weather").getJSONObject(0)

            return ForecastMVP.Forecast(
                temp = main.getDouble("temp").toInt(),
                icon = "https://openweathermap.org/img/wn/${weather.getString("icon")}@2x.png",
                minTemp = main.getDouble("temp_min").toInt(),
                maxTemp = main.getDouble("temp_max").toInt(),
                tempFeels = main.getDouble("feels_like").toInt(),
                status = weather.getString("description"),
                city = json.getString("name")
            )
        } catch (e: Exception) {
            return ForecastMVP.Forecast()
        }
    }
}