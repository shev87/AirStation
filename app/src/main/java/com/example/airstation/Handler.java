package com.example.airstation;

import android.util.Log;

import java.security.PrivateKey;
import java.security.spec.DSAPrivateKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.airstation.MainActivity.TAG;
import static com.example.airstation.MainActivity.textViewCO2;
import static com.example.airstation.MainActivity.textViewCO2Max;
import static com.example.airstation.MainActivity.textViewCO2Min;
import static com.example.airstation.MainActivity.textViewCount;
import static com.example.airstation.MainActivity.textViewH;
import static com.example.airstation.MainActivity.textViewHMax;
import static com.example.airstation.MainActivity.textViewHMin;
import static com.example.airstation.MainActivity.textViewPMax;
import static com.example.airstation.MainActivity.textViewPMin;
import static com.example.airstation.MainActivity.textViewT;
import static com.example.airstation.MainActivity.textViewP;
import static com.example.airstation.MainActivity.textViewTMax;
import static com.example.airstation.MainActivity.textViewTMin;
import static com.example.airstation.MainActivity.textViewTime;

public class Handler {
    static private String co2;
    static private String t;
    static private String hum;
    static private String p;
//323C87
    private static final byte DELAY = 10;

    static long countCommon;
    static int co2Int, co2IntMax, co2IntMin, countCo2Max, countCo2Min;
    static int humInt, humIntMax, humIntMin, countHumMax, countHumMin;
    static float tFloat, tFloatMax, tFloatMin, countTMax, countTMin;
    static float pFloat, pFloatMax, pFloatMin, countPMax, countPMin;

    static boolean isFirstStep = true;

    public static void showData(String s) {
        if (s != null) {
            s = s.trim();
            Log.i(TAG, s);
            try {
                String[] ss = s.split(" ");
                if (ss.length == 4) {
                    isCalculated(ss);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static boolean isCalculated(String[] array) {
        if (array.length != 4) return false;
        co2Int = Integer.parseInt(array[0]);
        Log.i(TAG, "Co2=" + co2Int);
        humInt = Integer.parseInt(array[1]);
        Log.i(TAG, "Hum=" + humInt);
        tFloat = Float.parseFloat(array[2]);
        Log.i(TAG, "T=" + tFloat);
        pFloat = Float.parseFloat(array[3]);
        Log.i(TAG, "P=" + pFloat);

        textViewCO2.setText(co2Int + "");
        textViewH.setText(humInt + "");
        textViewT.setText(tFloat + "");
        textViewP.setText(pFloat + "");

        if (isFirstStep) {
            isFirstStep = false;
            co2IntMax = co2Int;
            co2IntMin = co2Int;
            humIntMax = humInt;
            humIntMin = humInt;
            tFloatMax = tFloat;
            tFloatMin = tFloat;
            pFloatMax = pFloat;
            pFloatMin = pFloat;
            textViewCO2Max.setText(co2IntMax + "\r\n" + getTime());
            textViewCO2Min.setText(co2IntMin + "\r\n" + getTime());
            textViewHMax.setText(humIntMax + "\r\n" + getTime());
            textViewHMin.setText(humIntMin + "\r\n" + getTime());
            textViewTMax.setText(tFloatMax + "\r\n" + getTime());
            textViewTMin.setText(tFloatMin + "\r\n" + getTime());
            textViewPMax.setText(pFloatMax + "\r\n" + getTime());
            textViewPMin.setText(pFloatMin + "\r\n" + getTime());

        }
        textViewTime.setText(getTime());

        getExtreme();

        return true;
    }

    private static void getExtreme() {
        countCommon++;
        textViewCount.setText(countCommon + "");
        if (co2Int > co2IntMax) {
            countCo2Max++;
            if (countCo2Max > DELAY) {
                countCo2Max = 0;
                co2IntMax = co2Int;
                textViewCO2Max.setText(co2IntMax + "\r\n" + getTime());
            }
        } else countCo2Max = 0;

        if (co2Int < co2IntMin) {
            countCo2Min++;
            if (countCo2Min > DELAY) {
                countCo2Min = 0;
                co2IntMin = co2Int;
                textViewCO2Min.setText(co2IntMin + "\r\n" + getTime());
            }
        } else countCo2Min = 0;

        if (humInt > humIntMax) {
            countHumMax++;
            if (countHumMax > DELAY) {
                countHumMax = 0;
                humIntMax = humInt;
                textViewHMax.setText(humIntMax + "\r\n" + getTime());
            }
        } else countHumMax = 0;

        if (humInt < humIntMin) {
            countHumMin++;
            if (countHumMin > DELAY) {
                countHumMin = 0;
                humIntMin = humInt;
                textViewHMin.setText(humIntMin + "\r\n" + getTime());
            }
        } else countHumMin = 0;

        if (tFloat > tFloatMax) {
            countTMax++;
            if (countTMax > DELAY) {
                countTMax = 0;
                tFloatMax = tFloat;
                textViewTMax.setText(tFloatMax + "\r\n" + getTime());
            }
        } else countTMax = 0;

        if (tFloat < tFloatMin) {
            countTMin++;
            if (countTMin > DELAY) {
                countTMin = 0;
                tFloatMin = tFloat;
                textViewTMin.setText(tFloatMin + "\r\n" + getTime());
            }
        } else countTMin = 0;

        if (pFloat > pFloatMax) {
            countPMax++;
            if (countPMax > DELAY) {
                countPMax = 0;
                pFloatMax = pFloat;
                textViewPMax.setText(pFloatMax + "\r\n" + getTime());
            }
        } else countPMax = 0;

        if (pFloat < pFloatMin) {
            countPMin++;
            if (countPMin > DELAY) {
                countTMin = 0;
                pFloatMin = pFloat;
                textViewPMin.setText(pFloatMin + "\r\n" + getTime());
            }
        } else countPMin = 0;

    }

    private static String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
