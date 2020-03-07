package com.example.antitheft.structure;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.example.antitheft.MainActivity;

/**
 * This class' scope is used to send a command, or to manage incoming commands from
 * {@link GPSCommandReceiver}, replying to the one who sent the command.
 *
 * @author Francesco Bau'
 * @version 1.1
 * @since 25/02/2020
 */
public class LocateCommandHandler {

    private static final String LOCATE_COMMAND = "LOCATE";

    private GPSLocationManager locationManager;

    /**
     * Main constructor. It starts the {@link GPSLocationManager} instance.
     *
     * @see GPSLocationManager
     */
    public LocateCommandHandler() {
        locationManager = new GPSLocationManager();
    }

    /**
     * //TODO
     *
     * @param gpsLocationManager
     */
    public LocateCommandHandler(@NonNull GPSLocationManager gpsLocationManager) {
        this.locationManager = gpsLocationManager;
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
     * This method sends a given message (command) to a certain {@link SMSPeer}.
     *
     * @param smsPeer The destination {@link SMSPeer}. It can't be null.
     * @see com.eis.smslibrary.SMSMessage
     * @see com.eis.smslibrary.SMSManager
     */
    public void sendLocateCommandTo(@NonNull SMSPeer smsPeer) {
        String text = MainActivity.DEFAULT_COMMAND_PREFIX + " " + LOCATE_COMMAND;
        SMSMessage message = new SMSMessage(smsPeer, text);
        SMSManager.getInstance().sendMessage(message);
    }

    //**********RECEPTION AND REPLY**********

    /**
     * This method handles the received peer, from {@link GPSCommandReceiver#onMessageReceived(SMSMessage)},
     * replying with the last known GPS location, retrieved by {@link GPSLocationManager#getCurrentLocation(Context)}.
     *
     * @param peer The given peer. It can't be null.
     * @see com.eis.smslibrary.SMSMessage
     * @see GPSCommandReceiver#onMessageReceived(SMSMessage)
     * @see GPSLocationManager#getCurrentLocation(Context)
     */
    protected void onLocateCommandReceivedBy(@NonNull SMSPeer peer) {
        String currentLocation = locationManager.getCurrentLocation(MainActivity.getActivity());
        sendLocation(peer, currentLocation);
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
            text = new LocationParser().toString();
        else text = "Last known location: " + location;
        SMSManager.getInstance().sendMessage(new SMSMessage(smsPeer, text));
    }

}