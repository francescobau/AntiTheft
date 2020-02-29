package com.example.antitheft.structure;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

import org.junit.Before;
import org.junit.Test;

import static com.example.antitheft.structure.GPSCommandHandlerTest.TEXT_MESSAGE;
import static com.example.antitheft.structure.GPSCommandHandlerTest.VALID_TELEPHONE_NUMBER;

/**
 * @author Francesco Bau'
 * @since 29/02/2020
 */
public class GPSCommandReceiverTest {
    private SMSMessage smsMessage;
    private SMSPeer smsPeer;

    @Before
    public void init() {
        smsPeer = new SMSPeer(VALID_TELEPHONE_NUMBER);
        smsMessage = new SMSMessage(smsPeer, TEXT_MESSAGE);
    }

    @Test(expected = NullPointerException.class)
    public void onNullMessageReceived() {
        new GPSCommandReceiver().onMessageReceived(null);
    }

    @Test
    public void onMessageReceived() {
        new GPSCommandReceiver().onMessageReceived(smsMessage);
    }
}