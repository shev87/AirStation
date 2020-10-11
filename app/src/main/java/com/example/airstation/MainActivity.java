package com.example.airstation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = "myTag";

    static TextView textViewCO2;
    static TextView textViewH;
    static TextView textViewT;
    static TextView textViewP;

    ControllerBluetooth controllerBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewCO2 = findViewById(R.id.textViewCO2);
        textViewT = findViewById(R.id.textViewT);
        textViewH =findViewById(R.id.textViewH);
        textViewP =findViewById(R.id.textViewP);

        controllerBluetooth = new ControllerBluetooth();
        try {
            controllerBluetooth.init();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage().toString()+e.getCause().toString());
        }
    }
/*
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    */
}