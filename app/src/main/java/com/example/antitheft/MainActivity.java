package com.example.antitheft;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.eis.smslibrary.SMSMessage;
import com.example.antitheft.structure.GPSCommandHandler;
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

    String telephoneNumber;

    View sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();

        telephoneNumber = ((EditText) findViewById(R.id.telephoneNumber)).getText().toString();
        Log.d("TelephoneNumberCheck", "Telephone number: " + telephoneNumber);


        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                sendCommand(null);
            }
        });

        //Controllo per vedere che Location mi viene restituita
        String currentLoc = new GPSCommandHandler().getCurrentLocation(MainActivity.this);
        Log.d("MainActivity", "currentLoc: " + currentLoc);

    }


    /**
     * @param smsMessage The message to send.
     */
    public void sendCommand(SMSMessage smsMessage) {
        //TODO controllo per il comando: deve essere valido
        new GPSCommandHandler().sendCommand(smsMessage);
    }

    /**
     * //TODO
     *
     * @param smsMessage
     */
    public void isValidCommand(SMSMessage smsMessage) {
        //TODO

    }

    public String getCurrentLocation() {
        final LocationParser locationParser = new LocationParser();
        FusedLocationProviderClient client;
        client = LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                locationParser.setLocation(location);
            }
        });
        return locationParser.toString();
    }

}


