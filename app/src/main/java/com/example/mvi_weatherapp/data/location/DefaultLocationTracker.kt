package com.example.mvi_weatherapp.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.mvi_weatherapp.domain.location.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@ExperimentalCoroutinesApi
class DefaultLocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
) : LocationTracker {
    override suspend fun getCurrentLocation(): Location? {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        Log.d("Trackking", "hasAcessFineLocationPermission: $hasAccessFineLocationPermission")
        Log.d("Trackking", "hasAcessCoarseLocationPermission: $hasAccessCoarseLocationPermission")
        Log.d("Trackking", "isGPSEnabled: $isGPSEnabled")


        if (!hasAccessFineLocationPermission || !hasAccessCoarseLocationPermission || !isGPSEnabled) return null

        return suspendCancellableCoroutine { cont ->
            val priority = Priority.PRIORITY_HIGH_ACCURACY
            locationClient.getCurrentLocation(
                priority,
                CancellationTokenSource().token
            ).apply {
                if (isComplete) {
                    Log.d("Trackking", "lastLocation: $result")
                    if (isSuccessful) {
                        cont.resume(result) // result from locationClient.lastLocation
                    } else {
                        Log.d("Trackking", "couldn't get last location")
                        cont.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }

                addOnSuccessListener {
                    Log.d("Trackking", "successful: $it")
                    cont.resume(it)
                }
                addOnFailureListener {
                    Log.d("Trackking", "failure")
                    cont.resume(null)
                }
                addOnCanceledListener {
                    Log.d("Trackking", "cancelled")
                    cont.cancel()
                }
            }
        }
    }
}