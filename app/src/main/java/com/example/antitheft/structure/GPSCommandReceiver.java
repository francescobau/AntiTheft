package com.example.antitheft.structure;

import android.app.Activity;
import android.util.Log;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.listeners.SMSReceivedServiceListener;
import com.example.antitheft.MainActivity;

/**
 * @author Francesco Bau'
 * @version 0.1
 * <p>
 * This class captures all incoming messages.
 * @since 25/02/2020
 */
public class GPSCommandReceiver extends SMSReceivedServiceListener {

    /**
     * Listener, needed to obtain incoming messages, and to check if they need to be handled as
     * command or not.
     *
     * @param message The incoming message.
     */
    @Override
    public void onMessageReceived(SMSMessage message) {
        Log.d("GPSCommandReceiver", "Message received: " + message);
        if (!isValidCommand(message)) return;
        Log.d("GPSCommandReceiver", "Message received is a valid command");
        new GPSCommandHandler().onCommandReceived(message);
    }

    /**
     * This method checks if the given message is a command or not.
     *
     * @param smsMessage The message to check
     * @return true if message is a command, false otherwise.
     */
    private boolean isValidCommand(SMSMessage smsMessage) {
        return smsMessage.getData().contains("AT-1234 LOCATE");
    }

}
