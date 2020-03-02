package com.example.antitheft.structure;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * This service's scope is to request and retrieve the last known location.
 * @author Francesco Bau'
 * @version 1.0
 * @since 02/03/2020
 * @see android.app.Service
 */
public class GPSLocationManager extends Service {

    /**
     * If it won't be able to retrieve the location after DELAY * MAXIMUM_CHECK_TIMEOUT milliseconds,
     * it won't wait anymore.
     */
    private static final int DELAY = 500;
    private static final int MAXIMUM_CHECK_TIMEOUT = 10;

    private GPSLocationParser locationParser;

    /**
     * The client used to retrieve the last known {@link Location}.
     *
     * @see FusedLocationProviderClient for more info.
     */
    private FusedLocationProviderClient client;


    public GPSLocationManager() {
        locationParser = new GPSLocationParser();
        // Immediately prepare the client, in order to obtain the last known location.
        client = LocationServices.getFusedLocationProviderClient(this);
        /**
         * Callback to retrieve the last known location.
         * @see FusedLocationProviderClient#getLastLocation() to see how the last location is obtained.
         * @see com.google.android.gms.tasks.Task#addOnSuccessListener(OnSuccessListener)
         * to see more info about the used listener.
         */
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                locationParser.setLocation(location);
                Log.d("LOCATION","location instance: "+location);
                Log.d("LOCATION","location instance's extras: "+location.getExtras());
            }
        });
    }

    /**
     * This method retrieves the last known {@link Location}, parsed as String.
     * It's static because {@link GPSCommandHandler} needs this method, as well.
     *
     * @return The last known location.
     * @see Location
     * @see GPSLocationParser
     * @see GPSLocationParser#toString()
     */
    public String getCurrentLocation() {
        // Callback to retrieve the last known location.
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                locationParser.setLocation(location);
            }
        });
        int i = 0;
        // Waits for the callback, until DELAY * MAXIMUM_CHECK_TIMEOUT milliseconds.
        while (locationParser.isDefault() && i < MAXIMUM_CHECK_TIMEOUT) {
            // Location is NOT obtained.
            try {
                Thread.sleep(DELAY);
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return locationParser.toString();
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
