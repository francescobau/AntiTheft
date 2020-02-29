package com.example.antitheft.structure;

import android.location.Location;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test of {@link LocationParser} class.
 *
 * @author Francesco Bau'
 * @since 28/02/2020
 * @see LocationParser for more info about the tested class.
 */
public class LocationParserTest {

    private static final float NOT_DEFAULT_ACCURACY = Float.MAX_VALUE;
    LocationParser locationParser;

    double expectedLatitude = Double.MIN_VALUE;
    double expectedLongitude = Double.MIN_VALUE;

    /**
     * //TODO
     *
     * @see Location
     * @see LocationParser
     */
    @Before
    public void init() {
        locationParser = new LocationParser();
        Location location = new Location(LocationParser.DEFAULT_PROVIDER);
        location.setLatitude(expectedLatitude);
        location.setLongitude(expectedLongitude);
        locationParser.setLocation(location);
    }

    /**
     * //TODO
     *
     * @see Location
     * @see LocationParser
     */
    @Test
    public void testLatitude() {
        assertEquals(true, expectedLatitude == locationParser.getLatitude());
    }

    /**
     * //TODO
     *
     * @see Location
     * @see LocationParser
     */
    @Test
    public void testLongitude() {
        assertEquals(true, expectedLongitude == locationParser.getLongitude());
    }

    /**
     * //TODO
     *
     * @see Location
     * @see LocationParser
     */
    @Test
    public void testLatitudeA() {
        LocationParser parser = new LocationParser();
        Location location = new Location(LocationParser.DEFAULT_PROVIDER);
        location.setLatitude(expectedLatitude);
        parser.setLocation(location);
        assertEquals(true, expectedLatitude == parser.getLatitude());
    }

    /**
     * //TODO
     *
     * @see Location
     * @see LocationParser
     */
    @Test
    public void testLongitudeA() {
        LocationParser parser = new LocationParser();
        Location location = new Location(LocationParser.DEFAULT_PROVIDER);
        location.setLongitude(expectedLongitude);
        parser.setLocation(location);
        assertEquals(true, expectedLongitude == parser.getLongitude());
    }

    /**
     * Given a {@link Location} instance, the parser obtains latitude and longitude directly by that
     * instance.
     * <p>
     * A {@link Location} instance with Accuracy != 0.0f is enough to consider that instance not default.
     * Accuracy is added because, if someone is actually in a default location, the position
     * is considered not default, but acquired, instead.
     *
     * @see Location
     * @see LocationParser
     * @see Location#getAccuracy() to see more info about Accuracy.
     * @see Location#getLatitude(), {@link Location#getLongitude()} and their respective fields
     * to see their default values.
     * @see LocationParser#setLocation(Location) to see how {@link LocationParser} fields are set.
     */
    @Test
    public void testNotDefault() {
        Location location = new Location(LocationParser.DEFAULT_PROVIDER);
        location.setLatitude(expectedLatitude);
        location.setLongitude(expectedLongitude);
        // Supposing that we are actually there...
        location.setAccuracy(NOT_DEFAULT_ACCURACY);
        LocationParser parser = new LocationParser(location);
        assertEquals(false, parser.isDefault());
    }

    /**
     * //TODO
     *
     * @see Location
     * @see LocationParser
     */
    @Test
    public void testDefault() {
        Location location = new Location(LocationParser.DEFAULT_PROVIDER);
        LocationParser parser = new LocationParser(location);
        assertEquals(true, parser.isDefault());
    }

    /**
     * If someone wants to give default values (latitude, longitude, accuracy)
     * to the {@link Location} instance, the {@link LocationParser} will be considered default, as well.
     * <p>
     * But the contrary is not always true.
     *
     * @see Location
     * @see LocationParser
     */
    @Test
    public void testDefaultA() {
        Location location = new Location(LocationParser.DEFAULT_PROVIDER);
        location.setLatitude(LocationParser.DEFAULT_LOCATION);
        location.setLongitude(LocationParser.DEFAULT_LOCATION);
        LocationParser parser = new LocationParser(location);
        assertEquals(true, parser.isDefault());
    }

    @Test(expected = NullPointerException.class)
    public void testNullLocation() {
        locationParser = new LocationParser(null);
    }
}