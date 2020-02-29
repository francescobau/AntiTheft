package com.example.antitheft.structure;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Francesco Bau'
 * @since 29/02/2020
 */
public class GPSCommandHandlerTest {

    private SMSMessage smsMessage;
    private SMSPeer smsPeer;

    static final String LOCATION = "A";
    static final String VALID_TELEPHONE_NUMBER = "+393515186755";
    static final String TEXT_MESSAGE = "A";

    @Before
    public void init() {
        smsPeer = new SMSPeer(VALID_TELEPHONE_NUMBER);
        smsMessage = new SMSMessage(smsPeer, TEXT_MESSAGE);
    }

    @Test(expected = NullPointerException.class)
    public void sendNullMessage() {
        new GPSCommandHandler().sendMessage(null);
    }

    @Test(expected = NullPointerException.class)
    public void onNullCommandReceived() {
        new GPSCommandHandler().onCommandReceived(null);
    }

    @Test(expected = NullPointerException.class)
    public void sendLocationToNullPeer() {
        new GPSCommandHandler().sendLocation(null, LOCATION);
    }

    @Test
    public void onCommandReceived() {
        new GPSCommandHandler().onCommandReceived(smsMessage);
    }

    @Test
    public void sendLocation() {
        new GPSCommandHandler().sendLocation(smsPeer, LOCATION);
    }

}