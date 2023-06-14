package com.example.testtaskem.common

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Locale

class UserLocationService(
    private val context: Context
) {

    private var fusedLocationClient: FusedLocationProviderClient? = null

    suspend fun invoke(callback: (String) -> Unit) {

        var lastLocation: Any
        getLastLocation {
            lastLocation = it
            if (lastLocation is List<*>) {
                callback(
                    getUserCity(
                        (lastLocation as List<*>)[0] as Double,
                        (lastLocation as List<*>)[1] as Double
                    )
                )
            } else {
                callback(lastLocation.toString())
            }
        }
    }

    private suspend fun getUserCity(lat: Double, lon: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        var addresses: List<Address>?
        val city = CoroutineScope(Dispatchers.IO).async {
            addresses = geocoder.getFromLocation(lat, lon, 3)
            if (addresses!!.isNotEmpty()) {
                for (i in addresses?.indices!!) {
                    val address = addresses!![i]
                    if (address.locality != null && address.locality.isNotEmpty()) {
                        return@async address.locality
                    }
                }
            } else {
                return@async ""
            }
        }.await()

        return city.toString()
    }

    @SuppressLint("MissingPermission")
    private suspend fun getLastLocation(callback: suspend (Any) -> Unit) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                CoroutineScope(Dispatchers.Main).launch {
                    callback(listOf(latitude, longitude))
                }
            }
        }
    }
}