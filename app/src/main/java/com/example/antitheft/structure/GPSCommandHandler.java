package com.example.antitheft.structure;

import android.app.Activity;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

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
     * @param activity   Target Activity, in which location information is requested. It can't be null.
     */
    public void onCommandReceived(@NonNull SMSMessage smsMessage, @NonNull Activity activity) {
        SMSPeer smsPeer = smsMessage.getPeer();
        String currentLocation = getCurrentLocation(activity);

        sendLocation(smsPeer, currentLocation);
    }

    /**
     * This method retrieves the last known location.
     *
     * @param activity Target Activity, in which location information is requested. It can't be null.
     * @return The last known location.
     */
    public String getCurrentLocation(@NonNull Activity activity) {
        final LocationParser locationParser = new LocationParser();
        FusedLocationProviderClient client;
        client = LocationServices.getFusedLocationProviderClient(activity);
        client.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                locationParser.setLocation(location);
            }
        });
        return locationParser.toString();
    }

    /**
     * Method used to send our location to the peer who sent us the request.
     * The location is sent through SMS using the {@link com.eis.smslibrary.SMSManager} class.
     *
     * @param smsPeer  The peer who sent us the request. It can't be null.
     * @param location Current position of our device.
     */
    public void sendLocation(@NonNull SMSPeer smsPeer, @Nullable String location) {
        String text;
        if (location == null) text = LocationParser.UNKNOWN_LOCATION_MESSAGE;
        else text = "Target device's last known location:\n" + location;
        SMSMessage smsMessage = new SMSMessage(smsPeer, text);
        SMSManager.getInstance().sendMessage(smsMessage);
    }

}