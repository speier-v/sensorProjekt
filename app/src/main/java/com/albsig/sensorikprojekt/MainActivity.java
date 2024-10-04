package com.albsig.sensorikprojekt;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.albsig.sensorikprojekt.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnTextOutput.setOnClickListener(view -> loadFragment(TextOutputFragment.class));

        binding.btnGraph.setOnClickListener(view -> loadFragment(GraphFragment.class));
    }

    private void loadFragment(Class fragmentClass) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, fragmentClass, null)
                .commit();
    }
}