package com.albsig.sensorikprojekt;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

public class SensorsViewModel extends ViewModel {
    private static final String TAG = "ViewModel";
    private static final int LIST_SIZE_MAX = 50;
    private final Helpers helpers = new Helpers();

    private final MutableLiveData<ArrayList<String>> textData = new MutableLiveData<>();

    public static MutableLiveData<ArrayList<GyroSensorModel>> gyroValList = new MutableLiveData<>();
    public static MutableLiveData<ArrayList<GpsSensorModel>> gpsValList = new MutableLiveData<>();
    public static MutableLiveData<ArrayList<MicrophoneSensorModel>> microphoneValList = new MutableLiveData<>();

    public LiveData<ArrayList<String>> getTextData() {
        return textData;
    }

    public void setGyroData(GyroSensorModel newGyroData) {
        if(gyroValList.getValue() == null) {
            gyroValList.postValue(new ArrayList<>());
            return;
        }

        ArrayList<GyroSensorModel> tmpList = gyroValList.getValue();


        int tmpListSize = tmpList.size() - 1;
        if (tmpListSize > 0 ) {
            GyroSensorModel lastGyroData = tmpList.get(tmpListSize);
            getGyroLogs(lastGyroData, newGyroData);
        }

        //Too much information bc it is a listener
        //String strGyro = "Gyro - x " + newGyroData.getX() + " y " + newGyroData.getY() + " z " + newGyroData.getZ();
        //addToTextOutput(strGyro);

        if (tmpList.size() > LIST_SIZE_MAX) {
            tmpList.subList(1, tmpListSize);
            tmpList.add(newGyroData);
            gyroValList.postValue(tmpList);
            return;
        }

        tmpList.add(newGyroData);
        gyroValList.postValue(tmpList);
    }

    private void addToTextOutput(String textStr) {
        ArrayList<String> arrList;
        if(textData.getValue() != null) {
            arrList = textData.getValue();
        } else {
            arrList = new ArrayList<>();
        }

        arrList.add(textStr);
        textData.postValue(arrList);
    }


    public void getGyroLogs(GyroSensorModel lastGyroData, GyroSensorModel newGyroData) {
        if(!Objects.equals(lastGyroData.getX(), newGyroData.getX())) {
            Log.d(TAG, "X-Axis moved");
        }

        if(!Objects.equals(lastGyroData.getY(), newGyroData.getY())) {
            Log.d(TAG, "Y-Axis moved");
        }

        if(!Objects.equals(lastGyroData.getZ(), newGyroData.getZ())) {
            Log.d(TAG, "Z-Axis moved");
        }
    }

    public LiveData<ArrayList<GyroSensorModel>> getGyroData() {
        return gyroValList;
    }

    public void setGpsData(GpsSensorModel newGpsData) {
        ArrayList<GpsSensorModel> tmpList;
        if(gpsValList.getValue() != null) {
            tmpList = gpsValList.getValue();
        } else {
            tmpList = new ArrayList<>();
        }

        BigDecimal bdLong = helpers.roundDouble(newGpsData.getLongitude(), 4);
        BigDecimal bdLat = helpers.roundDouble(newGpsData.getLatitude(), 4);
        String strGps = "GPS - long. " + bdLong + " lat. " + bdLat;
        addToTextOutput(strGps);

        int tmpListSize = tmpList.size() - 1;
        if (tmpList.size() > LIST_SIZE_MAX) {
            tmpList.subList(1, tmpListSize);
            tmpList.add(newGpsData);
            gpsValList.postValue(tmpList);
            return;
        }

        tmpList.add(newGpsData);
        gpsValList.postValue(tmpList);
    }

    public LiveData<ArrayList<GpsSensorModel>> getGpsData() {
        return gpsValList;
    }

    public void setMicrophoneData(MicrophoneSensorModel microphoneSensorModel) {
        ArrayList<MicrophoneSensorModel> tmpList;
        if(microphoneValList.getValue() != null) {
            tmpList = microphoneValList.getValue();
        } else {
            tmpList = new ArrayList<>();
        }

        BigDecimal bdDb = helpers.roundDouble(microphoneSensorModel.getDb(), 4);
        String strMicrophone = "Microphone - db " + bdDb;
        addToTextOutput(strMicrophone);

        if (tmpList.size() > LIST_SIZE_MAX) {
            int tmpListSize = tmpList.size() - 1;
            tmpList.subList(1, tmpListSize);
            tmpList.add(microphoneSensorModel);
            microphoneValList.postValue(tmpList);
            return;
        }

        tmpList.add(microphoneSensorModel);
        microphoneValList.postValue(tmpList);
    }

    public LiveData<ArrayList<MicrophoneSensorModel>> getMicrophoneData() {
        return microphoneValList;
    }
}