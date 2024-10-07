package com.albsig.sensorikprojekt;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.albsig.sensorikprojekt.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SensorsViewModel sensorsViewModel;
    private MicrophoneSensorReader micReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorsViewModel = new ViewModelProvider(this).get(SensorsViewModel.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadFragment(TextOutputFragment.class);

        binding.btnTextOutput.setOnClickListener(view -> loadFragment(TextOutputFragment.class));

        binding.btnGraph.setOnClickListener(view -> loadFragment(GraphFragment.class));

        // Microphone Reader
        micReader = new MicrophoneSensorReader(this, sensorsViewModel);
        if (!micReader.checkMicrophonePermission()) {
            micReader.requestMicrophonePermission(this);
        } else {
            micReader.startMonitoring();
        }
    }

    private void loadFragment(Class fragmentClass) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, fragmentClass, null)
                .commit();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MicrophoneSensorReader.REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("MainActivity", "Microphone permission granted");
            } else {
                Log.d("MainActivity", "Microphone permission denied");
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        micReader.stopMonitoring();
    }
}