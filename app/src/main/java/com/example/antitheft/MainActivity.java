package com.example.antitheft;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.example.antitheft.structure.GPSCommandHandler;
import com.example.antitheft.structure.GPSCommandReceiver;
import com.example.antitheft.structure.LocationParser;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * //TODO
 * @author Francesco Bau'
 * @version 0.1
 * <p>
 * Main Activity. It takes UI commands as input, and, as an output, it gives them a certain action.
 * Its current scope is just to send SMS, based by what does the User decide to do.
 * @since 24/02/2020
 */
public class MainActivity extends AppCompatActivity {

    EditText telephoneField;
    View sendButton;

    // Context instance is not enough, it needs an Activity instance.
    // It's static because it's used on a static method.
    // That method is static because GPSCommandHandler needs that method, as well.
    private static Activity activity;

    private static final String APP_CODE = "AT";
    private static final int DEFAULT_PASSWORD = 1234;

    private static final String LOCATE_COMMAND = "LOCATE";

    // Full Default LOCATE command: "AT-1234 LOCATE"
    public static final String FULL_DEFAULT_LOCATE_COMMAND = APP_CODE + "-" + DEFAULT_PASSWORD + " " + LOCATE_COMMAND;

    /**
     * Necessary permissions: SEND_SMS, RECEIVE_SMS, WAKE_LOCK,
     * ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, ACCESS_BACKGROUND_LOCATION
     */
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.WAKE_LOCK,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION};

    private static final int REQUEST_CODE = 0;

    private static LocationParser locationParser = new LocationParser();
    private static FusedLocationProviderClient client;

    // If it won't be able to retrieve the location after DELAY * MAXIMUM_CHECK_TIMEOUT milliseconds,
    // it won't wait anymore.
    private static final int DELAY = 500;
    private static final int MAXIMUM_CHECK_TIMEOUT = 10;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();

        //Setting the listener
        SMSManager.getInstance().setReceivedListener(GPSCommandReceiver.class, context);
        Log.d("MainActivity", "Listener set");
        telephoneField = findViewById(R.id.telephoneNumber);
        sendButton = findViewById(R.id.sendButton);
        activity = this;

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean permissionsGranted = checkSelfPermission(Manifest.permission.SEND_SMS) == PERMISSION_GRANTED;
                if (permissionsGranted) {
                    String telephoneNumber = ((EditText) findViewById(R.id.telephoneNumber)).getText().toString();

                    SMSPeer peer = new SMSPeer(telephoneNumber);
                    if (peer == null)
                        Toast.makeText(context, "The peer is not valid.", Toast.LENGTH_SHORT).show();
                    else sendCommand(new SMSMessage(peer, FULL_DEFAULT_LOCATE_COMMAND));
                } else {
                    Toast.makeText(context, "This app needs at least SEND_SMS permissions in order to send the command via SMS.", Toast.LENGTH_SHORT).show();
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, REQUEST_CODE);
                }
            }
        });

        requestPermissions(PERMISSIONS, REQUEST_CODE);

        client = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(MainActivity.activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                locationParser.setLocation(location);
            }
        });

    }

    /**
     * This method sends the command
     *
     * @param smsMessage The message to send.
     */
    public void sendCommand(@Nullable SMSMessage smsMessage) {
        if (smsMessage != null)
            new GPSCommandHandler().sendCommand(smsMessage);
        else
            Log.d("SEND-COMMAND", "Failed to send command.");
    }

    /**
     * This method retrieves the last known location.
     *
     * @return The last known location.
     */
    public static String getCurrentLocation() {
        client.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                locationParser.setLocation(location);
            }
        });
        int i = 0;
        // Waits for the listener, until DELAY * MAXIMUM_CHECK_TIMEOUT milliseconds.
        while (locationParser.isDefault() && i < MAXIMUM_CHECK_TIMEOUT) {
            Log.d("LOCATION", "Location is NOT obtained. " + i);
            try {
                Thread.sleep(DELAY);
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d("LOCATION", "toString() of locationParser: " + locationParser.toString());
        return locationParser.toString();
    }

}