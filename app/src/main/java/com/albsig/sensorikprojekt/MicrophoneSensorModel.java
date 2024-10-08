package com.albsig.sensorikprojekt;

import java.time.LocalDateTime;

public class MicrophoneSensorModel {

    private final LocalDateTime time;
    private final Double db;

    MicrophoneSensorModel(Double db) {
        this.time = LocalDateTime.now();
        this.db = db;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Double getDb() {
        return db;
    }
}
