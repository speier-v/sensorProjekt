package com.albsig.sensorikprojekt;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;
import android.Manifest;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MicrophoneSensorReader {
    private static final String TAG = "MicrophoneSensorReader";
    private static final int SAMPLE_RATE = 44100;
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);
    private static final int INTERVAL_MS = 2000; //alle 2 Sekunden
    private static List<String> readings = new ArrayList<>();

    private AudioRecord audioRecord;
    private boolean isMonitoring;
    private Thread monitoringThread;
    private Context context;
    private SensorsViewModel viewModel;

    public MicrophoneSensorReader(Context context, SensorsViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Permission wasn't granted");
            return;
        }
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, BUFFER_SIZE);
        Log.d(TAG, "Create AudioRecorder");
    }

    public static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    public void startMonitoring() {
        if (isMonitoring) return;

        isMonitoring = true;
        Log.d(TAG, "Start monitoring");
        monitoringThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
                    Log.e(TAG, "AudioRecord initialization failed");
                    return;
                }

                audioRecord.startRecording();
                Log.d(TAG, "Start audio recording");
                short[] buffer = new short[BUFFER_SIZE / 2];

                while (isMonitoring) {
                    int read = audioRecord.read(buffer, 0, buffer.length);
                    Log.d(TAG, "Reading buffer");

                    if (read > 0) {
                        double decibelLevel = calculateDecibelLevel(buffer, read);
                        Log.d(TAG, "Current Noise Level: " + decibelLevel + " dB");
                        if (!String.valueOf(decibelLevel).contains("Infinity")) {
                            // Buffer für die Anfangswerte, wenn -Infinity als Dezibel gemessen werden
                            readings.add("Mikrophone-Level: "+String.valueOf(decibelLevel));
                            viewModel.setTextData(readings);
                        }

                        try {
                            Thread.sleep(INTERVAL_MS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                audioRecord.stop();
                Log.d(TAG, "Stop recording audio");
                audioRecord.release();
            }
        });

        monitoringThread.start();
    }

    public void stopMonitoring() {
        isMonitoring = false;
        Log.d(TAG, "Stop monitoring");

        if (monitoringThread != null) {
            try {
                monitoringThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private double calculateDecibelLevel(short[] buffer, int read) {
        long sum = 0;
        for (int i = 0; i < read; i++) {
            sum += buffer[i] * buffer[i];
        }
        double mean = (double) sum / read;
        double result = 10 * Math.log10(mean);

        return result;
    }

    public boolean checkMicrophonePermission() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestMicrophonePermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
        if (checkMicrophonePermission()) {
            startMonitoring();
        }
    }
}
