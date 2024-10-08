package com.albsig.sensorikprojekt;

import android.app.NotificationManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

public class GyroSensorReader {
    private static final String TAG = "GyroSensorReader";

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

                    GyroSensorModel gyro = new GyroSensorModel(x,y,z);
                    viewModel.setGyroData(gyro);

                    if(x > 7 || y > 7 || z > 7) {
                        Log.d(TAG, "Prepare to send notification");
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "ChannelID")
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setContentTitle("CRAZY ALERT!!!")
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText("You are crazy man !!!!"))
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                        Log.d(TAG, "Finish setting up NotificationCompat Builder");
                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(MainActivity.notificationId, builder.build());
                        Log.d(TAG, "Sent notification with id: " + MainActivity.notificationId);
                        MainActivity.notificationId += 1;
                    }
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
