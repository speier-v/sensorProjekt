package com.albsig.sensorikprojekt;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.util.Log;

import java.util.List;

public class GyroSensorReader {
    private static final String TAG = "GyroSensorReader";
    private static List<String> readings = SensorsViewModel.sensorValuesList;

    private final Context context;
    private final SensorsViewModel viewModel;

    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private SensorEventListener listener;

    public GyroSensorReader(Context context, SensorsViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
        Log.d(TAG, "Create GyroSensorReader");
        initSensorManager();
    }

    private void initSensorManager() {
        Log.d(TAG, "Init gyro-sensor manager");
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager != null) {
            gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }
    }

    public void gyroRegisterListener() {
        if (listener != null) {
            return;
        }

        listener = initSensorEventListener();
        sensorManager.registerListener(listener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public SensorEventListener initSensorEventListener() {
        return new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    public void gyroUnregisterListener() {
        if(listener == null) {
            return;
        }

        sensorManager.unregisterListener(listener);
        listener = null;
    }
}
