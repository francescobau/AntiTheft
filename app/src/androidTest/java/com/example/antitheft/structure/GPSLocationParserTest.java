package com.example.antitheft.structure;

import android.location.Location;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test of {@link GPSLocationParser} class.
 *
 * @author Francesco Bau'
 * @see GPSLocationParser for more info about the tested class.
 * @since 28/02/2020
 */
public class GPSLocationParserTest {

    private static final float NOT_DEFAULT_ACCURACY = Float.MAX_VALUE;
    GPSLocationParser locationParser;

    double expectedLatitude = Double.MAX_VALUE;
    double expectedLongitude = Double.MIN_VALUE;

    /**
     * Before starting the tests, the {@link GPSLocationParser} instance needs to be initialized.
     *
     * @see Location
     * @see GPSLocationParser
     */
    @Before
    public void init() {
        locationParser = new GPSLocationParser();
        Location location = new Location(locationParser.DEFAULT_PROVIDER);
        location.setLatitude(expectedLatitude);
        location.setLongitude(expectedLongitude);
        locationParser.setLocation(location);
    }

    /**
     * Testing the method {@link GPSLocationParser#getLatitude()}.
     *
     * @see GPSLocationParser
     * @see GPSLocationParser#getLatitude()
     */
    @Test
    public void testLatitude() {
        assertTrue(expectedLatitude == locationParser.getLatitude());
    }

    /**
     * Testing the method {@link GPSLocationParser#getLongitude()}.
     *
     * @see GPSLocationParser
     * @see GPSLocationParser#getLongitude()
     */
    @Test
    public void testLongitude() {
        assertTrue(expectedLongitude == locationParser.getLongitude());
    }

    /**
     * Alternative test of method {@link GPSLocationParser#getLatitude()}.
     * This one doesn't need the @Before method, and the {@link GPSLocationParser} instance is set ad-hoc.
     * <p>
     * Another difference is that the longitude is not set in this test.
     *
     * @see Location
     * @see GPSLocationParser
     * @see GPSLocationParser#getLatitude()
     */
    @Test
    public void testLatitudeA() {
        GPSLocationParser parser = new GPSLocationParser();
        Location location = new Location(locationParser.DEFAULT_PROVIDER);
        location.setLatitude(expectedLatitude);
        parser.setLocation(location);

        assertTrue(expectedLatitude == parser.getLatitude());
    }

    /**
     * Alternative test of method {@link GPSLocationParser#getLongitude()}.
     * This one doesn't need the @Before method, and the {@link GPSLocationParser} instance is set ad-hoc.
     * <p>
     * Another difference is that the latitude is not set in this test.
     *
     * @see Location
     * @see GPSLocationParser
     * @see GPSLocationParser#getLongitude()
     */
    @Test
    public void testLongitudeA() {
        GPSLocationParser parser = new GPSLocationParser();
        Location location = new Location(locationParser.DEFAULT_PROVIDER);
        location.setLongitude(expectedLongitude);
        parser.setLocation(location);

        assertTrue(expectedLongitude == parser.getLongitude());
    }

    /**
     * Given a {@link Location} instance, the parser obtains latitude and longitude directly by that
     * instance.
     * <p>
     * A {@link Location} instance with Accuracy != 0.0f is enough to consider that instance not default.
     * Accuracy is also set because, if someone is actually in a default location, the position
     * must not be considered default, but acquired, instead.
     *
     * @see Location
     * @see GPSLocationParser
     * @see Location#getAccuracy() to see more info about Accuracy.
     * @see Location#getLatitude(), {@link Location#getLongitude()} and their respective fields
     * to see their default values.
     * @see GPSLocationParser#setLocation(Location) to see how {@link GPSLocationParser} fields are set.
     */
    @Test
    public void testNotDefault() {
        Location location = new Location(locationParser.DEFAULT_PROVIDER);
        location.setLatitude(expectedLatitude);
        location.setLongitude(expectedLongitude);
        // Supposing that we are actually there...
        location.setAccuracy(NOT_DEFAULT_ACCURACY);
        GPSLocationParser parser = new GPSLocationParser(location);

        assertFalse(parser.isDefault());
    }

    /**
     * Testing {@link GPSLocationParser#isDefault()} method, after invoking
     * main constructor {@link GPSLocationParser#GPSLocationParser(Location)}, given a default
     * {@link Location} instance.
     * {@link GPSLocationParser#isDefault()} should return true, because latitude and longitude are
     * set with default values.
     *
     * @see Location
     * @see GPSLocationParser
     */
    @Test
    public void testDefault() {
        Location location = new Location(locationParser.DEFAULT_PROVIDER);
        GPSLocationParser parser = new GPSLocationParser(location);

        assertTrue(parser.isDefault());
    }

    /**
     * Alternative test of {@link GPSLocationParser#isDefault()} method.
     * If someone wants to set default values (latitude, longitude, accuracy)
     * to the {@link Location} instance, the {@link GPSLocationParser} will be considered default, as well.
     * <p>
     * But the contrary is not always true.
     *
     * @see Location
     * @see GPSLocationParser
     */
    @Test
    public void testDefaultA() {
        Location location = new Location(locationParser.DEFAULT_PROVIDER);
        location.setLatitude(expectedLatitude);
        location.setLongitude(expectedLongitude);
        // Back to default values...
        location.setLatitude(new Location(locationParser.DEFAULT_PROVIDER).getLatitude());
        location.setLongitude(new Location(locationParser.DEFAULT_PROVIDER).getLongitude());

        GPSLocationParser parser = new GPSLocationParser(location);

        assertTrue(parser.isDefault());
    }

    /**
     * Testing main constructor {@link GPSLocationParser#GPSLocationParser(Location)}, after giving
     * a null {@link Location} instance as a parameter.
     *
     * @see Location
     * @see GPSLocationParser
     * @see GPSLocationParser#GPSLocationParser(Location)
     * @see NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void testNullLocation() {
        locationParser = new GPSLocationParser(null);
    }
}