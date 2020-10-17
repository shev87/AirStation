package com.example.airstation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = "myTag";

    static TextView textViewCO2, textViewCO2Max, textViewCO2Min;
    static TextView textViewH, textViewHMax, textViewHMin;
    static TextView textViewT, textViewTMax, textViewTMin;
    static TextView textViewP, textViewPMax, textViewPMin;
    static TextView textViewTime, textViewCount;

    static ImageButton button;

    ControllerBluetooth controllerBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewCO2 = findViewById(R.id.textViewCO2);
        textViewCO2Max = findViewById(R.id.textViewMaxCO2);
        textViewCO2Min = findViewById(R.id.textViewMinCO2);
        textViewT = findViewById(R.id.textViewT);
        textViewTMax = findViewById(R.id.textViewMaxT);
        textViewTMin = findViewById(R.id.textViewMinT);
        textViewH = findViewById(R.id.textViewH);
        textViewHMax = findViewById(R.id.textViewMaxH);
        textViewHMin = findViewById(R.id.textViewMinH);
        textViewP = findViewById(R.id.textViewP);
        textViewPMax = findViewById(R.id.textViewMaxP);
        textViewPMin = findViewById(R.id.textViewMinP);
        textViewTime = findViewById(R.id.textView5);
        textViewCount = findViewById(R.id.textViewCount);

        textViewCO2.setText("---");
        textViewCO2Max.setText("---");
        textViewCO2Min.setText("---");
        textViewT.setText("---");
        textViewTMax.setText("---");
        textViewTMin.setText("---");
        textViewH.setText("---");
        textViewHMax.setText("---");
        textViewHMin.setText("---");
        textViewP.setText("---");
        textViewPMax.setText("---");
        textViewPMin.setText("---");

        button = findViewById(R.id.imageButton);

        controllerBluetooth = new ControllerBluetooth();
        try {
            controllerBluetooth.init();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage().toString() + e.getCause().toString());
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    controllerBluetooth.bluetoothSocket.close();
                    controllerBluetooth.init();
                    Handler.isFirstStep = true;
                    Handler.countCommon = 0;
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        };
        button.setOnClickListener(onClickListener);
    }
/*
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    */
}