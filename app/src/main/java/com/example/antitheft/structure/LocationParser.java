package com.example.antitheft.structure;

import android.location.Location;

import androidx.annotation.NonNull;

/**
 * @author Francesco Bau'
 * @version 0.1
 * This class retrieves the coordinates of a certain {@link android.location.Location}  , and has
 * a custom toString method.
 * @since 26/02/2020
 */
public class LocationParser {
    private static final String UNKNOWN_LOCATION_MESSAGE = "Location is NOT acquired.";
    private static final double DEFAULT_LOCATION = 0;
    private double latitude, longitude;
    private boolean acquired;

    /**
     * Default constructor: Latitude and Longitude are setted with default value, so it's not acquired.
     */
    public LocationParser() {
        latitude = longitude = DEFAULT_LOCATION;
        setAcquired(false);
    }

    /**
     * Main constructor. It sets the fields, given Latitude and Longitude.
     *
     * @param latitude  The given latitude.
     * @param longitude The given longitude.
     */
    public LocationParser(double latitude, double longitude) {
        setLocation(latitude, longitude);
    }

    /**
     * Alternative constructor. It extracts Latitude and Longitude from the {@link android.location.Location}
     * instance. If the instance doesn't have an acquired location, acquired is set to false.
     *
     * @param location The given {@link android.location.Location} instance. It can't be null.
     */
    public LocationParser(@NonNull Location location) {
        setLocation(location);
    }

    /**
     * The core of this class. It acquires latitude and longitude, and, after acquiring these
     * parameters, it sets acquired flag to true.
     *
     * @param latitude  The given latitude.
     * @param longitude The given longitude.
     */
    public void setLocation(double latitude, double longitude) {
        setLatitude(latitude);
        setLongitude(longitude);
        setAcquired(true);
    }

    /**
     * Overload of method setLocation(double,double).
     *
     * @param location the given {@link android.location.Location} instance. It can't be null.
     */
    public void setLocation(@NonNull Location location) {
        setLocation(location.getLatitude(), location.getLongitude());
        // Check if location is acquired or not. Location without Accuracy means
        // it's not acquired, but with default values, instead.
        if (!location.hasAccuracy()) setAcquired(false);
    }

    /**
     * Method to set the latitude.
     *
     * @param latitude The given latitude.
     */
    private void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Method to set the longitude.
     *
     * @param longitude The given longitude.
     */
    private void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Method to set the acquired flag.
     *
     * @param acquired Tells if a valid location is acquired (true), or not (false).
     */
    private void setAcquired(boolean acquired) {
        this.acquired = acquired;
    }

    /**
     * This method returns the current latitude instance.
     *
     * @return the current latitude instance.
     */
    public double getLatitude() {
        return this.latitude;
    }

    /**
     * This method returns the current longitude instance.
     *
     * @return the current longitude instance.
     */
    public double getLongitude() {
        return this.longitude;
    }

    /**
     * This method tells if location is acquired or not.
     *
     * @return true if acquired, false otherwise.
     */
    public boolean isAcquired() {
        return this.acquired;
    }

    /**
     * Override of method Object.toString(). It gives information of the current location, as a String.
     *
     * @return information of the current location.
     */
    public String toString() {
        if (!isAcquired())
            return UNKNOWN_LOCATION_MESSAGE;
        return "Latitude: " + getLatitude() + " Longitude: " + getLongitude();

    }

}