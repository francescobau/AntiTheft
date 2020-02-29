package com.example.antitheft.structure;

import android.location.Location;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LocationParserMockTest {

    LocationParser locationParser;
    double expectedLatitude = -10;
    double expectedLongitude = -20;
    boolean expectedDefault = true;

    final String TEST_PROVIDER = "TEST";

    @Before
    public void init() {
        locationParser = mock(LocationParser.class);
    }

    @Test
    public void setLocation() {
        locationParser.setLocation(expectedLatitude, expectedLongitude);
    }

    @Test
    public void setLocation1() {
        Location location = new Location(TEST_PROVIDER);
        location.setLatitude(expectedLatitude);
        location.setLongitude(expectedLongitude);
        locationParser.setLocation(location);
        assertEquals(true, locationParser.getLatitude() == expectedLatitude
                && locationParser.getLongitude() == expectedLongitude);
    }

    @Test
    public void getLatitude() {
        when(locationParser.getLatitude()).thenReturn(expectedLatitude);
        locationParser.setLocation(expectedLatitude, LocationParser.DEFAULT_LOCATION);
        assertEquals(true, locationParser.getLatitude() == expectedLatitude);
    }

    @Test
    public void getLongitude() {
        when(locationParser.getLongitude()).thenReturn(expectedLongitude);
        locationParser.setLocation(LocationParser.DEFAULT_LOCATION, expectedLongitude);
        assertEquals(true, locationParser.getLongitude() == expectedLongitude);
    }

    @Test
    public void isDefault() {
        when(locationParser.isDefault()).thenReturn(expectedDefault);
        locationParser.setLocation(new Location(TEST_PROVIDER));
        assertEquals(true, locationParser.isDefault());
    }

}

