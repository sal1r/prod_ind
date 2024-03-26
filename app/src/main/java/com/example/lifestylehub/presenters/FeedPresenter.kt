package com.example.lifestylehub.presenters

import com.example.lifestylehub.interfaces.FeedMVP
import com.example.lifestylehub.models.FeedModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedPresenter(private val view: FeedMVP.View): FeedMVP.Presenter {
    private val model: FeedMVP.Model = FeedModel()
    override fun loadPlaces(lat: Double, lon: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            val places = model.getPlaces(lat, lon)

            view.runInUiThread {
                view.addPlaces(places)
            }
        }
    }
}