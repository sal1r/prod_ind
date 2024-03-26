package com.example.lifestylehub.models

import com.example.lifestylehub.api.PlacesApi
import com.example.lifestylehub.interfaces.FeedMVP

class FeedModel: FeedMVP.Model {
    override fun getPlaces(lat: Double, lon: Double): List<FeedMVP.Place> {
        return PlacesApi.getPlaces(lat, lon)
    }
}