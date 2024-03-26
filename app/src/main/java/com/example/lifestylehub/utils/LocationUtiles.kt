package com.example.lifestylehub.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.lifestylehub.R

object LocationUtiles {
    // служит для запуска функций, работающих с местоположением и обработки связанных с этим ошибок
    fun runWithLL(context: Context,
                  callback: (lat: Double, lon: Double) -> Unit,
                  geoDisabledCallback: () -> Unit,
                  geoPermissionDeniedCallback: () -> Unit) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // проверяем наличие разрешения, если его нет, запускаем соответсвующий колбек
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            geoPermissionDeniedCallback()
            return
        }

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            geoDisabledCallback()
            return
        }

        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        // Проверка на наличие местоположения
        if (location != null) {
            callback(location.latitude, location.longitude)
        } else {
            // Если местоположение неизвестно, запросите обновление местоположения
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, object :
                LocationListener {
                override fun onLocationChanged(location: Location) {
                    callback(location.latitude, location.longitude)

                    // Остановить обновления местоположения после получения координат
                    locationManager.removeUpdates(this)
                }

                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            })
        }
    }

    fun requestPermissions(parent: ActivityResultCaller,
                                   context: Context
    ) {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val req = parent.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) {
                LocationObserver.notifyListeners()
            }

            req.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            LocationObserver.notifyListeners()
        }
    }
}

object LocationObserver {
    private val listners = mutableListOf<() -> Unit>()

    fun addListener(listener: () -> Unit) {
        listners.add(listener)
    }

    fun notifyListeners() {
        listners.forEach { it() }
    }
}