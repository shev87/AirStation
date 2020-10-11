package com.example.airstation;

import static com.example.airstation.MainActivity.textViewCO2;
import static com.example.airstation.MainActivity.textViewH;
import static com.example.airstation.MainActivity.textViewT;
import static com.example.airstation.MainActivity.textViewP;

public class Handler {

    public static void showData(String s) {
        if (s != null) {
            String co2 = s.substring(s.indexOf("2 = "), s.indexOf("ppm"));
            String[] ss = co2.split(" ");
            if (co2 != null) textViewCO2.setText(ss[ss.length-1]);
        }
    }
}
