package com.example.antitheft.structure;

import android.location.Location;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test of {@link LocationParser} class.
 *
 * @author Francesco Bau'
 * @see LocationParser for more info about the tested class.
 * @since 28/02/2020
 */
public class LocationParserTest {

    private static final float NOT_DEFAULT_ACCURACY = Float.MAX_VALUE;
    LocationParser locationParser;

    double expectedLatitude = Double.MAX_VALUE;
    double expectedLongitude = Double.MIN_VALUE;

    /**
     * Before starting the tests, the {@link LocationParser} instance needs to be initialized.
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
     * Testing the method {@link LocationParser#getLatitude()}.
     *
     * @see LocationParser
     * @see LocationParser#getLatitude()
     */
    @Test
    public void testLatitude() {
        assertEquals(true, expectedLatitude == locationParser.getLatitude());
    }

    /**
     * Testing the method {@link LocationParser#getLongitude()}.
     *
     * @see LocationParser
     * @see LocationParser#getLongitude()
     */
    @Test
    public void testLongitude() {
        assertEquals(true, expectedLongitude == locationParser.getLongitude());
    }

    /**
     * Alternative test of method {@link LocationParser#getLatitude()}.
     * This one doesn't need the @Before method, and the {@link LocationParser} instance is set ad-hoc.
     * <p>
     * Another difference is that the longitude is not set in this test.
     *
     * @see Location
     * @see LocationParser
     * @see LocationParser#getLatitude()
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
     * Alternative test of method {@link LocationParser#getLongitude()}.
     * This one doesn't need the @Before method, and the {@link LocationParser} instance is set ad-hoc.
     * <p>
     * Another difference is that the latitude is not set in this test.
     *
     * @see Location
     * @see LocationParser
     * @see LocationParser#getLongitude()
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
     * Accuracy is also set because, if someone is actually in a default location, the position
     * must not be considered default, but acquired, instead.
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
     * Testing {@link LocationParser#isDefault()} method, after invoking
     * main constructor {@link LocationParser#LocationParser(Location)}, given a default
     * {@link Location} instance.
     * {@link LocationParser#isDefault()} should return true, because latitude and longitude are
     * set with default values.
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
     * Alternative test of {@link LocationParser#isDefault()} method.
     * If someone wants to set default values (latitude, longitude, accuracy)
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
        location.setLatitude(expectedLatitude);
        location.setLongitude(expectedLongitude);
        // Back to default values...
        location.setLatitude(new Location(LocationParser.DEFAULT_PROVIDER).getLatitude());
        location.setLongitude(new Location(LocationParser.DEFAULT_PROVIDER).getLongitude());

        LocationParser parser = new LocationParser(location);
        assertEquals(true, parser.isDefault());
    }

    /**
     * Testing main constructor {@link LocationParser#LocationParser(Location)}, after giving
     * a null {@link Location} instance as a parameter.
     *
     * @see Location
     * @see LocationParser
     * @see LocationParser#LocationParser(Location)
     * @see NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void testNullLocation() {
        locationParser = new LocationParser(null);
    }
}