package com.example.antitheft;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.eis.smslibrary.SMSMessage;
import com.example.antitheft.structure.GPSCommandHandler;

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


}


