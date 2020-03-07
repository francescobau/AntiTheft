package com.example.antitheft.structure;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Preconditions;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * This service's scope is to request and retrieve the last known location.
 *
 * @author Francesco Bau'
 * @version 1.1
 * @see android.app.Service
 * @since 02/03/2020
 */
public class GPSLocationManager extends Service {

    /**
     * If it won't be able to retrieve the location after DELAY * MAXIMUM_CHECK_TIMEOUT milliseconds,
     * it won't wait anymore.
     */
    private static final int DELAY = 500;
    private static final int MAXIMUM_CHECK_TIMEOUT = 10;

    /**
     * The client used to retrieve the last known {@link Location}. It can't be null.
     *
     * @see FusedLocationProviderClient for more info.
     */
    @NonNull
    private static FusedLocationProviderClient client;

    /**
     * This method retrieves the last known {@link Location}, parsed as String.
     *
     * @return The last known location.
     * @see Location
     * @see LocationParser
     * @see LocationParser#toString()
     */
    public static String getCurrentLocation(@NonNull Context context) {
        LocationParser locationParser = new LocationParser();
        client = client = LocationServices.getFusedLocationProviderClient(context);
        // If client is null, it can't retrieve the last known location.
        Preconditions.checkNotNull(client, "Client is null. Failed to retrieve last known location.");

        /** Callback to retrieve the last known location.
         * @see FusedLocationProviderClient#getLastLocation()
         * @see com.google.android.gms.tasks.Task#addOnSuccessListener(OnSuccessListener)
         */
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

    /**
     * //TODO
     *
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
