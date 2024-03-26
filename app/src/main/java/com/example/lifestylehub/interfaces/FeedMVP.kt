package com.example.lifestylehub.interfaces

interface FeedMVP {
    interface View {
        fun addPlaces(places: List<FeedMVP.Place>)
        fun runInUiThread(runnable: Runnable)
    }

    interface Presenter {
        fun loadPlaces(lat: Double, lon: Double)
    }

    interface Model {
        fun getPlaces(lat: Double, lon: Double): List<FeedMVP.Place>
    }

    abstract class FeedItem()

    data class Place(
        val name: String = "",
        val address: String = "",
        val image: String = "",
        val fsqId: String = ""
    ) : FeedItem()
}