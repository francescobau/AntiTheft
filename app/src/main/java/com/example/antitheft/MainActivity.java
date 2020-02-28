package com.example.antitheft;

import android.Manifest;
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
    private static MainActivity activity;

    private static final String APP_CODE = "AT";
    private static final int DEFAULT_PASSWORD = 1234;

    private static final String LOCATE_COMMAND = "LOCATE";

    // Full Default LOCATE command: "AT-1234 LOCATE"
    public static final String FULL_DEFAULT_LOCATE_COMMAND = APP_CODE + "-" + DEFAULT_PASSWORD + " " + LOCATE_COMMAND;

    /**
     * Necessary permissions: SEND_SMS, RECEIVE_SMS,
     * ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, ACCESS_BACKGROUND_LOCATION
     */
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.WAKE_LOCK,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION};

    private static final int REQUEST_CODE = 0;

    LocationParser locationParser = new LocationParser();
    private FusedLocationProviderClient client;

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
                    //TODO mettere il controllo del peer perchè con la vecchia versione della smslibrary non c'è più il metodo simpatico
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
        client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
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
    //TODO Gestione asincronia.
    public String getCurrentLocation() {
        client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                locationParser.setLocation(location);
            }
        });
        int i = 10;
        while (!locationParser.isAcquired() && i > 0) {
            Log.d("LOCATION","Location is NOT obtained. "+i);
            try {
                Thread.sleep(100);
                i--;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d("LOCATION", "toString() of locationParser: " + locationParser.toString());
        return locationParser.toString();
    }

    /**
     * This method is needed to access this class' instance.
     *
     * @return The current MainActivity instance.
     */
    public static MainActivity getMainActivity() {
        return activity;
    }

}