package com.example.antitheft.structure;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.listeners.SMSReceivedServiceListener;

/**
 * @author Francesco Bau'
 * @version 0.1
 * <p>
 * This class captures all incoming messages.
 * @since 25/02/2020
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
