package com.albsig.sensorikprojekt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.albsig.sensorikprojekt.databinding.FragmentTextOutputBinding;

public class TextOutputFragment extends Fragment {

private FragmentTextOutputBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = FragmentTextOutputBinding.inflate(inflater, container, false);
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