package com.albsig.sensorikprojekt;

import java.time.LocalDateTime;

public class GpsSensorModel {

    private final LocalDateTime time;
    private final Double longitude;
    private final Double latitude;

    GpsSensorModel(Double longitude, Double latitude) {
        this.time = LocalDateTime.now();
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
}
