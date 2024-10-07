package com.albsig.sensorikprojekt;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.albsig.sensorikprojekt.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SensorsViewModel sensorsViewModel;
    private MicrophoneSensorReader micReader;
    private GpsSensorReader gpsReader;
    private static final int REQUEST_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorsViewModel = new ViewModelProvider(this).get(SensorsViewModel.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadFragment(TextOutputFragment.class);

        binding.btnTextOutput.setOnClickListener(view -> loadFragment(TextOutputFragment.class));

        binding.btnGraph.setOnClickListener(view -> loadFragment(GraphFragment.class));

        requestPermissions();
    }

    private void loadFragment(Class fragmentClass) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, fragmentClass, null)
                .commit();
    }


    private void requestPermissions() {
        String[] permissions = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
        } else {
            startSensors();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("MainActivity", permission + " permission granted");
                } else {
                    Log.d("MainActivity", permission + " permission denied");
                }
            }

            if (grantResults.length > 0) {
                boolean allPermissionsGranted = true;
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        allPermissionsGranted = false;
                        break;
                    }
                }
                if (allPermissionsGranted) {
                    Log.d("MainActivity", "All permissions granted");
                    startSensors();
                } else {
                    Log.d("MainActivity", "Not all permissions granted");
                }
            }
        }
    }

    private void startSensors() {
        micReader = new MicrophoneSensorReader(this, sensorsViewModel);
        micReader.startMonitoring();
        gpsReader = new GpsSensorReader(this, sensorsViewModel);
        gpsReader.startMonitoring();
    }
}