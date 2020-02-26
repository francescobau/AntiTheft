package com.example.antitheft.structure;

import android.app.Activity;
import android.content.Context;
import android.location.Location;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.example.antitheft.MainActivity;
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
     * //TODO
     *
     * @param smsMessage
     */
    public void sendCommand(SMSMessage smsMessage) {
        //TODO
    }

    //**********RECEPTION AND REPLY**********

    /**
     * //TODO
     *
     * @param smsMessage
     */
    //Questo metodo verrà chiamato dalla classe GPSCommandReceiver
    public void onCommandReceived(SMSMessage smsMessage, Activity activity) {
        SMSPeer smsPeer = smsMessage.getPeer();
        String currentLocation = getCurrentLocation(activity);

        sendLocation(smsPeer, currentLocation);
    }

    /**
     * //TODO
     *
     * @param activity
     * @return
     */
    public String getCurrentLocation(Activity activity) {
        final Double[] coordinates = new Double[2];
        FusedLocationProviderClient client;
        client = LocationServices.getFusedLocationProviderClient(activity);
        client.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                coordinates[0] = location.getLatitude();
                coordinates[1] = location.getLongitude();
            }
        });
        return parseCoordinates(coordinates);
    }

    /**
     * Method used to send our location to the peer who sent us the request.
     * The location is sent through SMS using the {@link com.eis.smslibrary.SMSManager} class.
     *
     * @param smsPeer  The peer who sent us the request
     * @param location Current position of our device
     */
    public void sendLocation(SMSPeer smsPeer, String location) {
        //TODO
    }

    /**
     * //TODO
     *
     * @param coordinates
     */
    public String parseCoordinates(Double[] coordinates) {
        return "latitude: " + coordinates[0] + " longitude: " + coordinates[1];
    }

}
