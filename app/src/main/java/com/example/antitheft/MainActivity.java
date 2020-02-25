package com.example.antitheft;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.eis.smslibrary.SMSMessage;
import com.example.antitheft.structure.GPSCommandHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO

    }

    /**
     * //TODO
     *
     * @param smsMessage
     */
    public void sendCommand(SMSMessage smsMessage){
        //TODO controllo per il comando: deve essere valido
        new GPSCommandHandler().sendCommand(smsMessage);
    }

    /**
     * //TODO
     *
     * @param smsMessage
     */
    public void isValidCommand(SMSMessage smsMessage){
        //TODO
    }
}
