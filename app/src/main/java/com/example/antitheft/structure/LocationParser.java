package com.example.antitheft.structure;

import android.location.Location;

import androidx.annotation.NonNull;

/**
 * This class retrieves the coordinates of a certain {@link android.location.Location} , and has
 * a custom toString method.
 *
 * @author Francesco Bau'
 * @version 1.1
 * @since 26/02/2020
 */
public class LocationParser {
    private static final String UNKNOWN_LOCATION_MESSAGE = "Location is NOT acquired.";
    static final String DEFAULT_PROVIDER = "Default";

    private double latitude, longitude;
    // If position is set with default values, defaultFlag returns true, it returns false otherwise.
    private boolean defaultFlag;

    /**
     * Default constructor: latitude and longitude are set with default values,
     * so the defaultFlag is set to true, as well.
     */
    public LocationParser() {
        Location location = new Location(DEFAULT_PROVIDER);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        setDefaultFlag(true);
    }

    /**
     * Main constructor. It extracts Latitude and Longitude from the {@link android.location.Location}
     * instance. If the instance has default values, defaultFlag is set to true.
     *
     * @param location The given {@link android.location.Location} instance. It can't be null.
     * @see Location
     */
    public LocationParser(@NonNull Location location) {
        setLocation(location);
    }

    /**
     * The core of this class. It acquires latitude and longitude.
     *
     * @param latitude  The given latitude.
     * @param longitude The given longitude.
     */
    private void setLocation(double latitude, double longitude) {
        setLatitude(latitude);
        setLongitude(longitude);
        setDefaultFlag(false);
    }

    /**
     * Overload of method setLocation(double,double).
     * It also checks if the {@link Location} instance is default or not, setting defaultFlag to true
     * if the instance has default values.
     *
     * @param location the given {@link android.location.Location} instance. It can't be null.
     * @see Location
     */
    public void setLocation(@NonNull Location location) {
        setLocation(location.getLatitude(), location.getLongitude());
        // Check if location has default values.
        if (location.getLatitude() == new Location(DEFAULT_PROVIDER).getLatitude() &&
                location.getLongitude() == new Location(DEFAULT_PROVIDER).getLongitude() &&
                !location.hasAccuracy()) setDefaultFlag(true);
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
     * @param defaultFlag Tells if a valid location is acquired (true), or not (false).
     */
    private void setDefaultFlag(boolean defaultFlag) {
        this.defaultFlag = defaultFlag;
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
    public boolean isDefault() {
        return this.defaultFlag;
    }

    /**
     * Override of method Object.toString().
     * It gives information of the current location, as a String.
     *
     * @return information of the current location.
     */
    public String toString() {
        if (isDefault())
            return UNKNOWN_LOCATION_MESSAGE;
        return "Latitude: " + getLatitude() + " Longitude: " + getLongitude();

    }

}