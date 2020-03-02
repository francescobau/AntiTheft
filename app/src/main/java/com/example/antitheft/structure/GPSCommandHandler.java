package com.example.antitheft.structure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

/**
 * This class' scope is used to send a command, or to manage incoming commands from
 * {@link GPSCommandReceiver}, replying to the one who sent the command.
 *
 * @author Francesco Bau'
 * @version 1.0
 * @since 25/02/2020
 */
public class GPSCommandHandler {

    private GPSLocationManager locationManager;

    /**
     * Main constructor. It starts the {@link GPSLocationManager} instance.
     *
     * @see GPSLocationManager
     */
    public GPSCommandHandler() {
        locationManager = new GPSLocationManager();
    }

    /**
     * This method returns the current {@link GPSLocationManager} instance.
     *
     * @return the current {@link GPSLocationManager} instance.
     * @see GPSLocationManager
     */
    public GPSLocationManager getLocationManager() {
        return locationManager;
    }

    //**********SENDING**********

    /**
     * This method sends a given message (command).
     *
     * @param smsMessage The message to send. It can't be null.
     * @see com.eis.smslibrary.SMSMessage
     * @see com.eis.smslibrary.SMSManager
     */
    public void sendMessage(@NonNull SMSMessage smsMessage) {
        SMSManager.getInstance().sendMessage(smsMessage);
    }

    //**********RECEPTION AND REPLY**********

    /**
     * This method handles the received command, from {@link GPSCommandReceiver#onMessageReceived(SMSMessage)},
     * replying with the last known GPS location, retrieved by {@link GPSLocationManager#getCurrentLocation()}.
     *
     * @param command The received command. It can't be null.
     * @see com.eis.smslibrary.SMSMessage
     * @see GPSCommandReceiver#onMessageReceived(SMSMessage)
     * @see GPSLocationManager#getCurrentLocation()
     */
    protected void onCommandReceived(@NonNull SMSMessage command) {
        String currentLocation = locationManager.getCurrentLocation();
        sendLocation(command.getPeer(), currentLocation);
    }

    /**
     * Method used to send a given location to the {@link com.eis.smslibrary.SMSPeer} who sent the request.
     * The location is sent through SMS using the {@link com.eis.smslibrary.SMSManager} class.
     *
     * @param smsPeer  The peer who sent us the request. It can't be null.
     * @param location Current position of our device.
     * @see com.eis.smslibrary.SMSPeer
     * @see com.eis.smslibrary.SMSManager
     */
    protected void sendLocation(@NonNull SMSPeer smsPeer, @Nullable String location) {
        String text;
        if (location == null)
            text = new GPSLocationParser().toString();
        else text = "Last known location: " + location;
        SMSManager.getInstance().sendMessage(new SMSMessage(smsPeer, text));
    }

}