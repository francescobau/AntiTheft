package com.example.antitheft.structure;

import android.location.Location;

import androidx.annotation.NonNull;

import com.eis.smslibrary.SMSPeer;


/**
 * @author Francesco Bau'
 * @version 0.1
 * <p>
 * This class manages the coordinates of a certain peer.
 * @since 25/02/2020
 */
public class GPSLocation {

    private static final String DEFAULT_GPS_PROVIDER = "GPSLocation";
    Location location;
    SMSPeer peer;

    /**
     * Constructor...//TODO
     *
     * @param peer
     * @param location
     */
    GPSLocation(@NonNull SMSPeer peer, @NonNull Location location) {
        this.peer = peer;
        this.location = location;
    }

    /**
     * This method is supposed to request the system to retrieve the current GPS Location.
     * <p>
     * But it currently returns a dummy Location, instead.
     *
     * @return A dummy Location instance.
     */
    public static Location getCurrentLocation() {
        return new Location(DEFAULT_GPS_PROVIDER);
    }

    /**
     * This method returns the current peer in this object.
     *
     * @return The current peer.
     */
    public SMSPeer getPeer() {
        return this.peer;
    }

    /**
     * This method returns the current location in this object.
     *
     * @return The current location.
     */
    public Location getLocation() {
        return this.location;
    }

}