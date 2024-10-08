package com.albsig.sensorikprojekt;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class GyroSensorModel {

    private final LocalDateTime time;
    private final Float xVal;
    private final Float yVal;
    private final Float zVal;

    GyroSensorModel(Float xVal, Float yVal, Float zVal) {
        this.time = LocalDateTime.now();
        this.xVal = xVal;
        this.yVal = yVal;
        this.zVal = zVal;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Float getX() {
        return xVal;
    }

    public Float getY() {
        return yVal;
    }

    public Float getZ() {
        return zVal;
    }
}
