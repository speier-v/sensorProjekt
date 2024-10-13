package com.albsig.sensorikprojekt;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

public class SensorsViewModel extends ViewModel {
    /*
    chart.getData().notifyDataChanged();
    chart.notifyDataSetChanged();
    chart.invalidate();
     */

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
        String strGyro = "Gyro - x " + newGyroData.getX() + " y " + newGyroData.getY() + " z " + newGyroData.getZ();
        addToTextOutput(strGyro);

        if (tmpList.size() > LIST_SIZE_MAX) {
            tmpList.subList(1, tmpListSize);
            tmpList.add(newGyroData);
            gyroValList.postValue(tmpList);
            return;
        }

        tmpList.add(newGyroData);
        gyroValList.postValue(tmpList);

        // chart refresh
        /*
        if (GraphFragment.binding != null
                && GraphFragment.binding.gyroscopeX != null
                && GraphFragment.binding.gyroscopeY != null
                && GraphFragment.binding.gyroscopeZ != null) {
            if (GraphFragment.binding.gyroscopeX.getData() != null) {
                GraphFragment.binding.gyroscopeX.getData().notifyDataChanged();
            }
            GraphFragment.binding.gyroscopeX.getData().notifyDataChanged();
            GraphFragment.binding.gyroscopeX.notifyDataSetChanged();
            GraphFragment.binding.gyroscopeX.invalidate();

            if (GraphFragment.binding.gyroscopeY.getData() != null) {
                GraphFragment.binding.gyroscopeY.getData().notifyDataChanged();
            }
            GraphFragment.binding.gyroscopeY.getData().notifyDataChanged();
            GraphFragment.binding.gyroscopeY.notifyDataSetChanged();
            GraphFragment.binding.gyroscopeY.invalidate();

            if (GraphFragment.binding.gyroscopeZ.getData() != null) {
                GraphFragment.binding.gyroscopeZ.getData().notifyDataChanged();
            }
            GraphFragment.binding.gyroscopeZ.getData().notifyDataChanged();
            GraphFragment.binding.gyroscopeZ.notifyDataSetChanged();
            GraphFragment.binding.gyroscopeZ.invalidate();
        }
        */
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

        // chart refresh
        /*
        if (GraphFragment.binding != null
                && GraphFragment.binding.gpsLong != null
                && GraphFragment.binding.gpsLat != null) {
            if (GraphFragment.binding.gpsLong.getData() != null) {
                GraphFragment.binding.gpsLong.getData().notifyDataChanged();
            }
            GraphFragment.binding.gpsLong.notifyDataSetChanged();
            GraphFragment.binding.gpsLong.invalidate();

            if (GraphFragment.binding.gpsLat.getData() != null) {
                GraphFragment.binding.gpsLat.getData().notifyDataChanged();
            }
            GraphFragment.binding.gpsLat.notifyDataSetChanged();
            GraphFragment.binding.gpsLat.invalidate();
        }
        */
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

        // chart refresh
        /*
        if (GraphFragment.binding != null && GraphFragment.binding.microphone != null) {
            if (GraphFragment.binding.microphone.getData() != null) {
                GraphFragment.binding.microphone.getData().notifyDataChanged();
            }
            GraphFragment.binding.microphone.notifyDataSetChanged();
            GraphFragment.binding.microphone.invalidate();
        }
        */
    }

    public LiveData<ArrayList<MicrophoneSensorModel>> getMicrophoneData() {
        return microphoneValList;
    }
}