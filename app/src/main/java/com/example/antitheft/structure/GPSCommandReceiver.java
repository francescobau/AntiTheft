package com.example.antitheft.structure;

import android.app.Activity;

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

    /**
     * This method checks if the given message is a command or not.
     *
     * @param smsMessage The message to check
     * @return true if message is a command, false otherwise.
     */
    private boolean isValidCommand(SMSMessage smsMessage) {
        if (smsMessage.getData().startsWith("AT-1234 LOCATE")) return true;
        return false;
    }

    /**
     * Listener, needed to obtain incoming messages, and to check if they need to be handled as
     * command or not.
     *
     * @param message The incoming message.
     */
    @Override
    public void onMessageReceived(SMSMessage message) {
        if (!isValidCommand(message)) return;

        new GPSCommandHandler().onCommandReceived(message, (Activity) getApplicationContext());
    }
}
