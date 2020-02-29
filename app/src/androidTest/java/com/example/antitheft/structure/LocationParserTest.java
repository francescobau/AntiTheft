package com.example.antitheft.structure;

import android.location.Location;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LocationParserTest {

    LocationParser locationParser;

    double expectedLatitude = -10;
    double expectedLongitude = -20;

    @Before
    public void init() {
        locationParser = new LocationParser();
        locationParser.setLocation(expectedLatitude, expectedLongitude);
    }

    @Test
    public void testLatitude() {
        if (locationParser.getLatitude() != expectedLatitude)
            fail();
    }

    @Test
    public void testLongitude() {
        if (locationParser.getLongitude() != expectedLongitude)
            fail();
    }

    @Test
    public void testAcquiredA() {
        LocationParser parser = new LocationParser(expectedLatitude, expectedLongitude);
        // Inserting data manually, with just latitude and longitude, is considered acquired.
        assertEquals(false, parser.isDefault());
    }

    @Test
    public void testAcquiredB() {
        Location location = new Location(LocationParser.DEFAULT_PROVIDER);
        location.setLatitude(expectedLatitude);
        location.setLongitude(expectedLongitude);
        LocationParser parser = new LocationParser(location);
        assertEquals(false, parser.isDefault());
    }

    @Test
    public void testDefault() {
        Location location = new Location(LocationParser.DEFAULT_PROVIDER);
        LocationParser parser = new LocationParser(location);
        assertEquals(true, parser.isDefault());
    }

    @Test(expected = NullPointerException.class)
    public void testNullLocation() {
        locationParser = new LocationParser(null);
    }


}