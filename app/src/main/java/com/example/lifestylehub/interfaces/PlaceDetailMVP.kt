package com.example.lifestylehub.interfaces

interface PlaceDetailMVP {
    interface View {
        fun showPlaceDetail(placeDetail: PlaceDetail)
        fun runInUiThread(action: Runnable)
    }

    interface Presenter {
        fun loadPlaceDetail(fsqId: String)
    }

    interface Model {

        fun getPlaceDetail(fsqId: String): PlaceDetail
    }

    data class PlaceDetail(
        val name: String = "",
        val address: String = "",
        val contacts: String = "",
        val mainImage: String = "",
        val otherImages: List<String> = emptyList(),
        val categories: List<String> = emptyList()
    )
}