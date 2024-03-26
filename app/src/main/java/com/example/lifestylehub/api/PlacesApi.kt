package com.example.lifestylehub.api

import com.example.lifestylehub.interfaces.FeedMVP
import com.example.lifestylehub.interfaces.PlaceDetailMVP
import org.json.JSONArray
import org.json.JSONObject

object PlacesApi {
    const val API_KEY = "fsq3l0wTmV6LYhcO267oDVWAVqHOXsk1hzPK8PTB8K/K3O8="
    private val client = okhttp3.OkHttpClient()

    private fun photosRequest(fsqId: String, limit: Int): okhttp3.Request =
        okhttp3.Request.Builder()
            .url("https://api.foursquare.com/v3/places/" +
                    "$fsqId/photos?limit=$limit")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", API_KEY)
            .build()


    fun getPlaces(lat: Double, lon: Double): List<FeedMVP.Place> {
        val places: MutableList<FeedMVP.Place> = mutableListOf()

        val placeRequest = okhttp3.Request.Builder()
            .url("https://api.foursquare.com/v3/places/search?ll=$lat,$lon&radius=100000&categories=10000,14003&limit=10")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", API_KEY)
            .build()

        try {
            val response = client.newCall(placeRequest).execute()

            val result = JSONObject(response.body?.string() ?: "{}")
            val responsePlaces = result.getJSONArray("results")

            for (i in 0 until responsePlaces.length()) {
                val place = responsePlaces.getJSONObject(i)

                val photosResponse = client.newCall(photosRequest(
                    place.getString("fsq_id"), 1
                )).execute()

                val photo = JSONArray(photosResponse.body?.string() ?: "{}").getJSONObject(0)

                places.add(
                    FeedMVP.Place(
                        name = place.getString("name"),
                        address = place.getJSONObject("location").getString("formatted_address"),
                        image = photo.getString("prefix") + "1080x720" + photo.getString("suffix"),
                        fsqId = place.getString("fsq_id")
                    )
                )
            }

        } catch (e: Exception) {
            return emptyList()
        }

        return places
    }

    fun getPlaceDetail(fsqId: String): PlaceDetailMVP.PlaceDetail {
        val placeDetailRequest = okhttp3.Request.Builder()
            .url("https://api.foursquare.com/v3/places/$fsqId")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", API_KEY)
            .build()



        try {
            val response = client.newCall(placeDetailRequest).execute()
            val result = JSONObject(response.body?.string() ?: "{}")

            val categories = mutableListOf<String>()

            val categoriesArray = result.getJSONArray("categories")
            for (i in 0 until categoriesArray.length()) {
                val category = categoriesArray.getJSONObject(i)
                categories.add(category.getString("name"))
            }

            val imagesResponse = client.newCall(photosRequest(
                result.getString("fsq_id"),
                10
            )).execute()

            val images = JSONArray(imagesResponse.body?.string() ?: "{}")

            var mainImage = ""
            val imagesList = mutableListOf<String>()

            for (i in 0 until images.length()) {
                val image = images.getJSONObject(i)
                val strImage = image.getString("prefix") + "1080x720" + image.getString("suffix")

                if (i == 0) {
                    mainImage = strImage
                    continue
                }

                imagesList.add(strImage)
            }

            return PlaceDetailMVP.PlaceDetail(
                name = result.getString("name"),
                address = result.getJSONObject("location").getString("formatted_address"),
                contacts = result.optString("email", ""),
                mainImage = mainImage,
                otherImages = imagesList,
                categories = categories
            )
        } catch (e: Exception) {
            return PlaceDetailMVP.PlaceDetail()
        }
    }
}