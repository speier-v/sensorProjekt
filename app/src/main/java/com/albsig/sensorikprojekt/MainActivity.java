package com.albsig.sensorikprojekt;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.albsig.sensorikprojekt.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding binding;
private SensorsViewModel sensorsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorsViewModel = new ViewModelProvider(this).get(SensorsViewModel.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadFragment(TextOutputFragment.class);

        binding.btnTextOutput.setOnClickListener(view -> loadFragment(TextOutputFragment.class));

        binding.btnGraph.setOnClickListener(view -> loadFragment(GraphFragment.class));
    }

    private void loadFragment(Class fragmentClass) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, fragmentClass, null)
                .commit();
    }
}