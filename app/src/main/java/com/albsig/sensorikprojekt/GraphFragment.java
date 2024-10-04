package com.albsig.sensorikprojekt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.albsig.sensorikprojekt.databinding.FragmentGraphBinding;

public class GraphFragment extends Fragment {

private FragmentGraphBinding binding;
private SensorsViewModel sensorsViewModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = FragmentGraphBinding.inflate(inflater, container, false);
      sensorsViewModel = new ViewModelProvider(requireActivity()).get(SensorsViewModel.class);
      return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}