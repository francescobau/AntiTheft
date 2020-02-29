package com.example.antitheft.structure;

import android.location.Location;

import org.junit.After;
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

    @After
    public void testLatitude() {
        if (locationParser.getLatitude() != expectedLatitude)
            fail();
    }

    @After
    public void testLongitude() {
        if (locationParser.getLongitude() != expectedLongitude)
            fail();
    }

    /**
     * @Before public void testLocationParser() {
     * locationParser = mock(LocationParser.class);
     * when(locationParser.getLatitude()).thenReturn(expectedLatitude);
     * when(locationParser.getLongitude()).thenReturn(expectedLongitude);
     * when(locationParser.isAcquired()).thenReturn(expectedDefault);
     * }
     */
    @Test
    public void testAcquiredA() {
        LocationParser locationParser = new LocationParser(expectedLatitude, expectedLongitude);
        // Inserting data manually, with just latitude and longitude, is considered acquired.
        assertEquals(false, locationParser.isDefault());
    }

    @Test
    public void testAcquiredB() {
        Location location = new Location(LocationParser.DEFAULT_PROVIDER);
        location.setLatitude(expectedLatitude);
        location.setLongitude(expectedLongitude);
        LocationParser locationParser = new LocationParser(location);
        assertEquals(false, locationParser.isDefault());
    }

    @Test
    public void testDefault() {
        Location location = new Location(LocationParser.DEFAULT_PROVIDER);
        LocationParser locationParser = new LocationParser(location);
        assertEquals(true, locationParser.isDefault());
    }

    @Test(expected = NullPointerException.class)
    public void testNullLocation() {
        locationParser = new LocationParser(null);
    }

}