package com.example.antitheft.structure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smslibrary.listeners.SMSReceivedServiceListener;
import com.example.antitheft.MainActivity;

/**
 * This class captures all incoming messages. It extends abstract class
 * {@link com.eis.smslibrary.listeners.SMSReceivedServiceListener}.
 *
 * @author Francesco Bau'
 * @version 1.1
 * @see com.eis.smslibrary.listeners.SMSReceivedServiceListener
 * @since 25/02/2020
 */
public class GPSCommandReceiver extends SMSReceivedServiceListener {

    private LocateCommandHandler commandHandler;

    /**
     * Main constructor. It starts the {@link LocateCommandHandler} instance.
     *
     * @see LocateCommandHandler
     */
    public GPSCommandReceiver() {
        commandHandler = new LocateCommandHandler();
    }

    /**
     * Listener, needed to obtain all incoming messages, and to check if they need to be handled as
     * commands or not.
     *
     * @param message The incoming message.
     * @see com.eis.smslibrary.SMSMessage
     * @see LocateCommandHandler
     * @see LocateCommandHandler#onLocateCommandReceivedBy(SMSPeer)
     */
    @Override
    public void onMessageReceived(@Nullable SMSMessage message) {
        if (message == null) return;
        if (!isValidCommand(message)) return;
        commandHandler.onLocateCommandReceivedBy(message.getPeer());
    }

    /**
     * This method checks if the given message is a command or not.
     *
     * @param smsMessage The message to check. It can't be null.
     * @return true if message is a command, false otherwise.
     * @see com.eis.smslibrary.SMSMessage
     * @see com.eis.smslibrary.SMSMessage#getData()
     */
    private boolean isValidCommand(@NonNull SMSMessage smsMessage) {
        return smsMessage.getData().contains(MainActivity.DEFAULT_COMMAND_PREFIX);
    }

}
