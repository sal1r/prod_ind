package com.example.lifestylehub.models

import com.example.lifestylehub.api.PlacesApi
import com.example.lifestylehub.interfaces.PlaceDetailMVP

class PlaceDetailModel: PlaceDetailMVP.Model {
    override fun getPlaceDetail(fsqId: String): PlaceDetailMVP.PlaceDetail {
        val cachedPlaceDetail = getCachedPlaceDetail(fsqId)
        if (cachedPlaceDetail != null) return cachedPlaceDetail

        val placeDetail = PlacesApi.getPlaceDetail(fsqId)
        cash[fsqId] = placeDetail
        return placeDetail
    }

    companion object {
        private val cash: MutableMap<String, PlaceDetailMVP.PlaceDetail> = mutableMapOf()

        private fun getCachedPlaceDetail(fsqId: String): PlaceDetailMVP.PlaceDetail? = cash[fsqId]
    }
}