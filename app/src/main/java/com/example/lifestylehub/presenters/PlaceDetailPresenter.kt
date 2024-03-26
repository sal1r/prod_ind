package com.example.lifestylehub.presenters

import com.example.lifestylehub.interfaces.PlaceDetailMVP
import com.example.lifestylehub.models.PlaceDetailModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaceDetailPresenter(private val view: PlaceDetailMVP.View): PlaceDetailMVP.Presenter {
    private val model: PlaceDetailMVP.Model = PlaceDetailModel()
    override fun loadPlaceDetail(fsqId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val placeDetail = model.getPlaceDetail(fsqId)
            view.runInUiThread { view.showPlaceDetail(placeDetail) }
        }
    }
}