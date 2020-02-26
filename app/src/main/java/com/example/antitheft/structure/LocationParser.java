package com.example.antitheft.structure;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Francesco Bau'
 * @version 0.1
 * This class retrieves the coordinates of a certain <link>android.Location</link> , and has
 * a custom toString.
 * @since 26/02/2020
 */
class LocationParser {
    private static final double DEFAULT_LOCATION = 0;
    private double latitude, longitude;

    /**
     * Main constructor. It sets the fields, given Latitude and Longitude.
     *
     * @param latitude
     * @param longitude
     */
    public LocationParser(@Nullable double latitude, @Nullable double longitude) {
        setLatitude(latitude);
        setLongitude(longitude);
    }

    /**
     * Alternative constructor. It extracts Latitude and Longitude from the <link>android.Location</link>
     * instance. It can't be null.
     *
     * @param location The given <link>android.Location</link> instance.
     */
    public LocationParser(@NonNull Location location) {
        setLocation(location);
    }

    /**
     * Default constructor: Latitude and Longitude are setted with default value.
     */
    public LocationParser() {
        latitude = longitude = DEFAULT_LOCATION;
    }

    public void setLocation(@NonNull Location location) {
        setLatitude(location.getLatitude());
        setLongitude(location.getLongitude());
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public String toString(){
        return "Latitude: " + getLatitude() + " Longitude: " + getLongitude();
    }

}