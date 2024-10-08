package com.albsig.sensorikprojekt;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Helpers {

    public BigDecimal roundDouble(double val, int roundTo) {
        BigDecimal bdVal = new BigDecimal(Double.toString(val));
        bdVal = bdVal.setScale(roundTo, RoundingMode.HALF_UP);
        return bdVal;
    }
}
