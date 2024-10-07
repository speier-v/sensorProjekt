package com.albsig.sensorikprojekt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.albsig.sensorikprojekt.databinding.FragmentTextOutputBinding;

public class TextOutputFragment extends Fragment {

    private FragmentTextOutputBinding binding;
    private SensorsViewModel sensorsViewModel;
    private TextView textView;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
      binding = FragmentTextOutputBinding.inflate(inflater, container, false);
      textView = binding.getRoot().findViewById(R.id.textOutputTV);
      sensorsViewModel = new ViewModelProvider(requireActivity()).get(SensorsViewModel.class);

      sensorsViewModel.getTextData().observe(getViewLifecycleOwner(), textList -> {
          StringBuilder formattedText = new StringBuilder();
          for (String text : textList) {
              formattedText.append(text).append("\n");
          }
          textView.setText(formattedText.toString());
      });

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