package com.example.antitheft.structure;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
    public void onMessageReceived(@Nullable SMSMessage message) {
        if (message == null) return;
        Log.d("GPSCommandReceiver", "Message received: " + message);
        if (!isValidCommand(message)) return;
        Log.d("GPSCommandReceiver", "Message received is a valid command");
        new GPSCommandHandler().onCommandReceived(message);
    }

    /**
     * This method checks if the given message is a command or not.
     *
     * @param smsMessage The message to check. It can't be null.
     * @return true if message is a command, false otherwise.
     */
    private boolean isValidCommand(@NonNull SMSMessage smsMessage) {
        boolean flag = smsMessage.getData().contains(MainActivity.FULL_DEFAULT_LOCATE_COMMAND);
        return flag;
    }

}
