package com.albsig.sensorikprojekt;

import android.content.Context;
import android.media.AudioRecord;
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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.time.ZoneOffset;
import java.util.ArrayList;

public class GraphFragment extends Fragment {

    private static final String TAG = "Graph";
    public static FragmentGraphBinding binding;
    private SensorsViewModel sensorsViewModel;
    private Context context;
    private boolean isMonitoring;
    private Thread monitoringThread;
    private static final int INTERVAL_MS = 5000;

    private LineDataSet gyroXChart;
    private LineDataSet gyroYChart;
    private LineDataSet gyroZChart;
    private LineDataSet gpsLongChart;
    private LineDataSet gpsLatChart;
    private LineDataSet micChart;

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
        setFabRefresh();
        setGyroCharts();
        setGPSCharts();
        setMicrophoneCharts();
    }

    private void setFabRefresh() {
        binding.fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGyroCharts();
                setGPSCharts();
                setMicrophoneCharts();
            }
        });
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

        gyroXChart = new LineDataSet(entries, "Gyroscope-X");
        gyroXChart.setColor(ContextCompat.getColor(context , android.R.color.holo_blue_dark));
        gyroXChart.setValueTextColor(ContextCompat.getColor(context, android.R.color.black));
        LineData lineData = new LineData(gyroXChart);
        binding.gyroscopeX.setData(lineData);
        binding.gyroscopeX.getDescription().setEnabled(false);
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

        gyroYChart = new LineDataSet(entries, "Gyroscope-Y");
        gyroYChart.setColor(ContextCompat.getColor(context , android.R.color.holo_blue_dark));
        gyroYChart.setValueTextColor(ContextCompat.getColor(context, android.R.color.black));
        LineData lineData = new LineData(gyroYChart);
        binding.gyroscopeY.setData(lineData);
        binding.gyroscopeY.getDescription().setEnabled(false);
        binding.gyroscopeY.invalidate();
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

        gyroZChart = new LineDataSet(entries, "Gyroscope-Z");
        gyroZChart.setColor(ContextCompat.getColor(context , android.R.color.holo_blue_dark));
        gyroZChart.setValueTextColor(ContextCompat.getColor(context, android.R.color.black));
        LineData lineData = new LineData(gyroZChart);
        binding.gyroscopeZ.setData(lineData);
        binding.gyroscopeZ.getDescription().setEnabled(false);
        binding.gyroscopeZ.invalidate();
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

        gpsLongChart = new LineDataSet(entries, "GPS-Longitude");
        gpsLongChart.setColor(ContextCompat.getColor(context , android.R.color.holo_blue_dark));
        gpsLongChart.setValueTextColor(ContextCompat.getColor(context, android.R.color.black));
        LineData lineData = new LineData(gpsLongChart);
        binding.gpsLong.setData(lineData);
        binding.gpsLong.getDescription().setEnabled(false);
        binding.gpsLong.invalidate();
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

        gpsLatChart = new LineDataSet(entries, "GPS-Latitude");
        gpsLatChart.setColor(ContextCompat.getColor(context , android.R.color.holo_blue_dark));
        gpsLatChart.setValueTextColor(ContextCompat.getColor(context, android.R.color.black));
        LineData lineData = new LineData(gpsLatChart);
        binding.gpsLat.setData(lineData);
        binding.gpsLat.getDescription().setEnabled(false);
        binding.gpsLat.invalidate();
    }

    private void setMicrophoneCharts() {
        ArrayList<MicrophoneSensorModel> micData = sensorsViewModel.getMicrophoneData().getValue();
        if (micData == null) {
            return;
        }
        setMicrophoneChart(micData);
    }

    private void setMicrophoneChart(ArrayList<MicrophoneSensorModel> micData) {
        Log.d(TAG, "Create Microphone chart");
        ArrayList<Entry> entries = new ArrayList<>();

        for (MicrophoneSensorModel micModel : micData) {
            float decibel = micModel.getDb().floatValue();
            float time = (float) micModel.getTime().toEpochSecond(ZoneOffset.UTC);
            entries.add(new Entry(time, decibel));
            Log.d(TAG, "time :" + time + "      decibel" + decibel);
        }

        micChart = new LineDataSet(entries, "Microphone");
        micChart.setColor(ContextCompat.getColor(context , android.R.color.holo_blue_dark));
        micChart.setValueTextColor(ContextCompat.getColor(context, android.R.color.black));
        LineData lineData = new LineData(micChart);

        binding.microphone.setData(lineData);
        binding.microphone.getDescription().setEnabled(false);
        binding.microphone.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}