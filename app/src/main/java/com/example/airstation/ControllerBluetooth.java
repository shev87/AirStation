package com.example.airstation;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.ParcelUuid;
import android.util.Log;

import java.util.Set;

import static com.example.airstation.MainActivity.TAG;

public class ControllerBluetooth extends Activity {
    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    public BluetoothSocket bluetoothSocket;

    public void init() {
        if (bluetoothAdapter != null) {
            if (bluetoothAdapter.isEnabled()) {
                if (pairedDevices.size() > 0) {
                    Object[] devices = pairedDevices.toArray();
                    BluetoothDevice device = (BluetoothDevice) devices[0];
                    Log.i(TAG, "Our device = " + device.getName());
                    device.fetchUuidsWithSdp();
                    ParcelUuid[] uuids = device.getUuids();
                    Log.i(TAG,"UUID our device:");
                    for (ParcelUuid u : uuids) {
                        Log.i(TAG,"" + u.getUuid());
                    }
                    try {
                        //socket = bluetooth.listenUsingRfcommWithServiceRecord(device.getName(), uuids[0].getUuid()).accept();
                        Log.i(TAG, "start to create socket");
                        bluetoothSocket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
                        Log.i(TAG, "try to connect");
                        bluetoothSocket.connect();
                        Log.i(TAG,"Connection is OK!!!");

                        MyBluetoothService myBluetoothService = new MyBluetoothService(bluetoothSocket);

                    } catch (Exception e) {
                        Log.i(TAG,"\r\n" + e.getMessage() + e.getCause());
                    }
                } else {
                    Log.i(TAG, "No appropriate paired devices.");
                }
            } else {
                Log.i(TAG, "Bluetooth is disabled.");
            }
        }
    }

    public ControllerBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //if (bluetoothAdapter != null) onDestroy();
        if (!bluetoothAdapter.isEnabled()) {
            // Bluetooth выключен. Предложим пользователю включить его.
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            new MainActivity().startActivityForResult(enableBtIntent, 1);
        }
        bluetoothAdapter.getState();
        pairedDevices = bluetoothAdapter.getBondedDevices();
    }
}
