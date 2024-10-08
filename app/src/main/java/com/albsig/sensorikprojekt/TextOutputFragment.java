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

import java.util.List;

public class TextOutputFragment extends Fragment {

    private static final String TAG = "Viewmodel";

    private FragmentTextOutputBinding binding;
    private SensorsViewModel sensorsViewModel;
    private TextView textView;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
      binding = FragmentTextOutputBinding.inflate(inflater, container, false);
      sensorsViewModel = new ViewModelProvider(requireActivity()).get(SensorsViewModel.class);
      return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = binding.textOutputTV;
        sensorsViewModel.getTextData().observe(getViewLifecycleOwner(), textList -> {
            StringBuilder formattedText = new StringBuilder();
            int start = Math.max(textList.size() - 40, 0);
            List<String> recentTexts = textList.subList(start, textList.size());
            for (String text : recentTexts) {
                formattedText.append(text).append("\n");
            }
            textView.setText(formattedText.toString());
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}