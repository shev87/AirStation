package com.example.airstation;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.example.airstation.MainActivity.TAG;

public class MyBluetoothService extends Activity {
    private static final String TAG = "myTag";
    public String s = null;

    // Defines several constants used when transmitting messages between the
    // service and the UI.
    private interface MessageConstants {
        public static final int MESSAGE_READ = 0;
        public static final int MESSAGE_WRITE = 1;
        public static final int MESSAGE_TOAST = 2;

        // ... (Add other message types here as needed.)
    }

    public MyBluetoothService(BluetoothSocket mmSocket) {
        ConnectedThread connectedThread = new ConnectedThread(mmSocket);
        connectedThread.start();
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private byte[] mmBuffer; // mmBuffer store for the stream

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams; using temp objects because
            // member streams are final.
            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
                Log.i(TAG, "Error occurred when creating input stream", e);
            }
          /*  try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.i(TAG, "Error occurred when creating output stream", e);
            }*/

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        public void run() {
            mmBuffer = new byte[1024];
            int numBytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                try {
                    synchronized (this) {
                        try {
                            wait(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // Read from the InputStream.
                        while (mmInStream.available() > 0) {
                            numBytes = mmInStream.read(mmBuffer);
                            s = new String(mmBuffer);
                            s = s.substring(0, numBytes);
                        }
                        Log.i(TAG, "Received message: " + s);
                        if (s != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Handler.showData(s);
                                }
                            });
                        }
                        // Send the obtained bytes to the UI activity.
                    /*Message readMsg = handler.obtainMessage(
                            MessageConstants.MESSAGE_READ, numBytes, -1,
                            mmBuffer);
                    readMsg.sendToTarget();*/
                    }

                } catch (IOException e) {
                    Log.d(TAG, "Input stream was disconnected", e);
                    break;
                }
            }
        }

        // Call this from the main activity to send data to the remote device.
        /*public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);

                // Share the sent message with the UI activity.
                Message writtenMsg = handler.obtainMessage(
                        MessageConstants.MESSAGE_WRITE, -1, -1, mmBuffer);
                writtenMsg.sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Error occurred when sending data", e);

                // Send a failure message back to the activity.
                Message writeErrorMsg =
                        handler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                Bundle bundle = new Bundle();
                bundle.putString("toast",
                        "Couldn't send data to the other device");
                writeErrorMsg.setData(bundle);
                handler.sendMessage(writeErrorMsg);
            }
        }*/

        // Call this method from the main activity to shut down the connection.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.i(TAG, "Could not close the connect socket", e);
            }
        }
    }
}
