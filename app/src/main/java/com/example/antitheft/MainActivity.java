package com.example.antitheft;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
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
    static Activity activity;

    private static final String APP_CODE = "AT";
    private static final int DEFAULT_PASSWORD = 1234;

    private static final String LOCATE_COMMAND = "LOCATE";

    // Full Default LOCATE command: "AT-1234 LOCATE"
    public static final String FULL_DEFAULT_LOCATE_COMMAND = APP_CODE + "-" + DEFAULT_PASSWORD + " " + LOCATE_COMMAND;

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
                String telephoneNumber = ((EditText) findViewById(R.id.telephoneNumber)).getText().toString();
                //TODO mettere il controllo del peer perchè con la vecchia versione della smslibrary non c'è più il metodo simpatico
                SMSPeer peer = new SMSPeer(telephoneNumber);
                sendCommand(new SMSMessage(peer, FULL_DEFAULT_LOCATE_COMMAND));
            }
        });
    }

    /**
     * This method sends the command
     *
     * @param smsMessage The message to send. It can't be null.
     */
    public void sendCommand(@NonNull SMSMessage smsMessage) {
        if (smsMessage != null)
            new GPSCommandHandler().sendCommand(smsMessage);
    }

    /**
     * This method retrieves the last known location.
     *
     * @return The last known location.
     */
    public static String getCurrentLocation() {
        final LocationParser locationParser = new LocationParser();
        FusedLocationProviderClient client;
        client = LocationServices.getFusedLocationProviderClient(activity);
        client.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                locationParser.setLocation(location);
            }
        });
        return locationParser.toString();
    }

}
