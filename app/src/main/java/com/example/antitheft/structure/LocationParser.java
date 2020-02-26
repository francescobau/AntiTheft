package com.example.antitheft.structure;

import android.location.Location;

import androidx.annotation.NonNull;

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
    private boolean acquired;

    /**
     * Default constructor: Latitude and Longitude are setted with default value.
     */
    public LocationParser() {
        latitude = longitude = DEFAULT_LOCATION;
        setAcquired(false);
    }

    /**
     * Main constructor. It sets the fields, given Latitude and Longitude.
     *
     * @param latitude The given latitude. It can't be null.
     * @param longitude The given longitude. It can't be null.
     */
    public LocationParser(@NonNull double latitude, @NonNull double longitude) {
        setLocation(latitude, longitude);
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

    public void setLocation(@NonNull Location location) {
        setLocation(location.getLatitude(),location.getLongitude());
    }

    public void setLocation(@NonNull double latitude, @NonNull double longitude){
        setLatitude(latitude);
        setLongitude(longitude);
        setAcquired(true);
    }

    /**
     * Method to set the latitude.
     * @param latitude The given latitude. It can't be null.
     */
    private void setLatitude(@NonNull double latitude) {
        this.latitude = latitude;
    }

    /**
     * Method to set the longitude.
     * @param longitude The given longitude. It can't be null.
     */
    private void setLongitude(@NonNull double longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @param acquired Tells if a valid location is acquired, or not.
     */
    private void setAcquired(@NonNull boolean acquired){
        this.acquired = acquired;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }
    public boolean isAcquired(){
        return acquired;
    }

    public String toString(){
        if(!isAcquired())
            return "Location is NOT acquired.";
        return "Latitude: " + getLatitude() + " Longitude: " + getLongitude();
    }

}