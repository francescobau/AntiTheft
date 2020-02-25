package com.example.antitheft.structure;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSMessageHandler;
import com.eis.smslibrary.listeners.SMSReceivedServiceListener;

/**
 *
 */
public class GPSCommandReceiver extends SMSReceivedServiceListener {

    //TODO

    private boolean isValidCommand(SMSMessage smsMessage) {
        //TODO: controllare se il messaggio ricevuto è un comando valido, il come lo decidi tu
        return false;
    }

    @Override
    public void onMessageReceived(SMSMessage message) {

    }
}
