package com.albsig.sensorikprojekt;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.List;

public class GpsSensorReader implements LocationListener {
    private static final String TAG = "GpsSensorReader";
    private static final long MIN_TIME_MS = 5000;
    private static final float MIN_DISTANCE = 0;

    private final LocationManager locationManager;
    private final SensorsViewModel sensorsViewModel;
    private final Context context;

    public GpsSensorReader(Context context, SensorsViewModel sensorsViewModel) {
        this.sensorsViewModel = sensorsViewModel;
        this.context = context;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void startMonitoring() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Location permission not granted");
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_MS,
                MIN_DISTANCE,
                this
        );

        Log.d(TAG, "GPS Monitoring started");
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        GpsSensorModel gpsSensorModel = new GpsSensorModel(location.getLongitude(), location.getLatitude());
        String s = "Location updated:" + "Long:" + gpsSensorModel.getLongitude() + ", Lat: " + gpsSensorModel.getLatitude();
        Log.d(TAG, s);
        sensorsViewModel.setGpsData(gpsSensorModel);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) { }

    @Override
    public void onProviderDisabled(@NonNull String provider) { }
}
