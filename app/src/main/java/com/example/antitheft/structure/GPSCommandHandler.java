package com.example.antitheft.structure;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.example.antitheft.MainActivity;

/**
 * @author Francesco Bau'
 * @version 0.1
 * <p>
 * This class is used to send commands.
 * @since 25/02/2020
 */
public class GPSCommandHandler {

    //**********SENDING**********

    /**
     * This method sends a message.
     *
     * @param smsMessage The message to send. It can't be null.
     */
    public void sendCommand(@NonNull SMSMessage smsMessage) {
        SMSManager.getInstance().sendMessage(smsMessage);
    }

    //**********RECEPTION AND REPLY**********

    /**
     * This method handles the received command, replying with the last known GPS location.
     *
     * @param smsMessage The received message. It can't be null.
     */
    void onCommandReceived(@NonNull SMSMessage smsMessage) {
        Log.d("GPSCommandHandler", "Current Location: " + MainActivity.getCurrentLocation());
        sendLocation(smsMessage.getPeer(), MainActivity.getCurrentLocation());
    }

    /**
     * Method used to send our location to the peer who sent us the request.
     * The location is sent through SMS using the {@link com.eis.smslibrary.SMSManager} class.
     *
     * @param smsPeer  The peer who sent us the request. It can't be null.
     * @param location Current position of our device.
     */
    private void sendLocation(@NonNull SMSPeer smsPeer, @Nullable String location) {
        String text;
        if (location == null) text = new LocationParser().toString();
        else text = "Target device's last known location:\n" + location;
        Log.d("GPSCommandHandler", "Text sent back: " + text);
        SMSManager.getInstance().sendMessage(new SMSMessage(smsPeer, text));
    }

}