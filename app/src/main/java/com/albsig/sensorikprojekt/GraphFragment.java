package com.albsig.sensorikprojekt;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.albsig.sensorikprojekt.databinding.FragmentGraphBinding;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.time.ZoneOffset;
import java.util.ArrayList;

public class GraphFragment extends Fragment {

    private static final String TAG = "Graph";
    private FragmentGraphBinding binding;
    private SensorsViewModel sensorsViewModel;
    private Context context;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

      binding = FragmentGraphBinding.inflate(inflater, container, false);
      sensorsViewModel = new ViewModelProvider(requireActivity()).get(SensorsViewModel.class);
      return binding.getRoot();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setGyroCharts();
        setGPSCharts();
    }

    private void setGyroCharts() {
        ArrayList<GyroSensorModel> gyroData = sensorsViewModel.getGyroData().getValue();
        if (gyroData == null) {
            return;
        }
        setGyroXChart(gyroData);
        setGyroYChart(gyroData);
        setGyroZChart(gyroData);
    }

    private void setGyroXChart(ArrayList<GyroSensorModel> gyroData) {
        Log.d(TAG, "Create Gyro-X chart");
        ArrayList<Entry> entries = new ArrayList<>();

        for (GyroSensorModel gpsModel : gyroData) {
            float val = gpsModel.getX();
            float time = (float) gpsModel.getTime().toEpochSecond(ZoneOffset.UTC);
            entries.add(new Entry(time, val));
            Log.d(TAG, "time: " + time + "     x " + val);
        }

        LineDataSet dataSet = new LineDataSet(entries, "Gyroscope-X");
        dataSet.setColor(ContextCompat.getColor(context , android.R.color.holo_blue_dark));
        dataSet.setValueTextColor(ContextCompat.getColor(context, android.R.color.black));
        LineData lineData = new LineData(dataSet);
        binding.gyroscopeX.setData(lineData);
        binding.gpsLat.getDescription().setEnabled(false);
        binding.gyroscopeX.invalidate();
    }

    private void setGyroYChart(ArrayList<GyroSensorModel> gyroData) {
        Log.d(TAG, "Create Gyro-Y chart");
        ArrayList<Entry> entries = new ArrayList<>();

        for (GyroSensorModel gpsModel : gyroData) {
            float val = gpsModel.getY();
            float time = (float) gpsModel.getTime().toEpochSecond(ZoneOffset.UTC);
            entries.add(new Entry(time, val));
            Log.d(TAG, "time: " + time + "     y " + val);
        }

        LineDataSet dataSet = new LineDataSet(entries, "Gyroscope-Y");
        dataSet.setColor(ContextCompat.getColor(context , android.R.color.holo_blue_dark));
        dataSet.setValueTextColor(ContextCompat.getColor(context, android.R.color.black));
        LineData lineData = new LineData(dataSet);
        binding.gyroscopeX.setData(lineData);
        binding.gpsLat.getDescription().setEnabled(false);
        binding.gyroscopeX.invalidate();
    }

    private void setGyroZChart(ArrayList<GyroSensorModel> gyroData) {
        Log.d(TAG, "Create Gyro-Z chart");
        ArrayList<Entry> entries = new ArrayList<>();

        for (GyroSensorModel gpsModel : gyroData) {
            float val = gpsModel.getZ();
            float time = (float) gpsModel.getTime().toEpochSecond(ZoneOffset.UTC);
            entries.add(new Entry(time, val));
            Log.d(TAG, "time: " + time + "     z " + val);
        }

        LineDataSet dataSet = new LineDataSet(entries, "Gyroscope-Z");
        dataSet.setColor(ContextCompat.getColor(context , android.R.color.holo_blue_dark));
        dataSet.setValueTextColor(ContextCompat.getColor(context, android.R.color.black));
        LineData lineData = new LineData(dataSet);
        binding.gyroscopeX.setData(lineData);
        binding.gpsLat.getDescription().setEnabled(false);
        binding.gyroscopeX.invalidate();
    }

    private void setGPSCharts() {
        ArrayList<GpsSensorModel> gpsData = sensorsViewModel.getGpsData().getValue();
        if (gpsData == null) {
            return;
        }
        setGPSLongChart(gpsData);
        setGPSLatChart(gpsData);
    }

    private void setGPSLongChart(ArrayList<GpsSensorModel> gpsData) {
        Log.d(TAG, "Create GPS-long chart");
        ArrayList<Entry> entries = new ArrayList<>();

        for (GpsSensorModel gpsModel : gpsData) {
            float longitude = gpsModel.getLongitude().floatValue();
            float time = (float) gpsModel.getTime().toEpochSecond(ZoneOffset.UTC);
            entries.add(new Entry(time, longitude));
            //Log.d(TAG, "time :" + time + "      longitude" + longitude);
        }

        LineDataSet dataSet = new LineDataSet(entries, "GPS-Longitude");
        dataSet.setColor(ContextCompat.getColor(context , android.R.color.holo_blue_dark));
        dataSet.setValueTextColor(ContextCompat.getColor(context, android.R.color.black));
        LineData lineData = new LineData(dataSet);
        binding.gpsLat.setData(lineData);
        binding.gpsLat.getDescription().setEnabled(false);
        binding.gpsLat.invalidate();
    }

    private void setGPSLatChart(ArrayList<GpsSensorModel> gpsData) {
        Log.d(TAG, "Create GPS-lat chart");
        ArrayList<Entry> entries = new ArrayList<>();

        for (GpsSensorModel gpsModel : gpsData) {
            float latitude = gpsModel.getLatitude().floatValue();
            float time = (float) gpsModel.getTime().toEpochSecond(ZoneOffset.UTC);
            entries.add(new Entry(time, latitude));
            //Log.d(TAG, "time :" + time + "      latitude" + latitude);
        }

        LineDataSet dataSet = new LineDataSet(entries, "GPS-Latitude");
        dataSet.setColor(ContextCompat.getColor(context , android.R.color.holo_blue_dark));
        dataSet.setValueTextColor(ContextCompat.getColor(context, android.R.color.black));
        LineData lineData = new LineData(dataSet);
        binding.gpsLong.setData(lineData);
        binding.gpsLat.getDescription().setEnabled(false);
        binding.gpsLong.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}