package com.albsig.sensorikprojekt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
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
    private GyroSensorReader gyroReader;
    private GpsSensorReader gpsReader;
    private static final int REQUEST_PERMISSIONS = 1;
    public static int notificationId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Viewmodel
        sensorsViewModel = new ViewModelProvider(this).get(SensorsViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //Bind view
        setContentView(binding.getRoot());
        //standard view
        loadFragment(TextOutputFragment.class);
        //click listener for navigation
        binding.btnTextOutput.setOnClickListener(view -> loadFragment(TextOutputFragment.class));
        binding.btnGraph.setOnClickListener(view -> loadFragment(GraphFragment.class));

        requestPermissions();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        String channelId = "ChannelID";
        CharSequence channelName = "Notification";
        String channelDescription = "Channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setDescription(channelDescription);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gyroReader.gyroRegisterListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gyroReader.gyroUnregisterListener();
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
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.POST_NOTIFICATIONS
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
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
        gyroReader = new GyroSensorReader(this, sensorsViewModel);
        gyroReader.gyroRegisterListener();
    }
}