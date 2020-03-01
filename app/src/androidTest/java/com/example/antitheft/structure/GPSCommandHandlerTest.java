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

    private GPSCommandHandler handler;

    static final String LOCATION = "";
    // WARNING: insert a VALIID telephoneNumber, because it sends an SMS to that telephone number.
    static final String VALID_TELEPHONE_NUMBER = "+393515186755";
    static final String TEXT_MESSAGE = "";


    /**
     * Initialization of the fields, before testing.
     *
     * @see com.eis.smslibrary.SMSPeer
     * @see com.eis.smslibrary.SMSMessage
     */
    @Before
    public void init() {
        smsPeer = new SMSPeer(VALID_TELEPHONE_NUMBER);
        smsMessage = new SMSMessage(smsPeer, TEXT_MESSAGE);
        handler = new GPSCommandHandler();
    }

    /**
     * Test what happens if there's an attempt to send a null {@link SMSMessage} .
     * <p>
     * {@link NullPointerException} is expected to be thrown, because of the @NonNull annotation.
     *
     * @see NullPointerException
     * @see com.eis.smslibrary.SMSMessage
     * @see GPSCommandHandler#sendMessage(SMSMessage)
     */
    @Test(expected = NullPointerException.class)
    public void sendNullMessage() {
        handler.sendMessage(null);
    }

    /**
     * Test what happens if a null {@link SMSMessage} is received in method
     * {@link GPSCommandHandler#onCommandReceived(SMSMessage)}.
     * <p>
     * {@link NullPointerException} is expected to be thrown, because of the @NonNull annotation.
     *
     * @see NullPointerException
     * @see com.eis.smslibrary.SMSMessage
     * @see GPSCommandHandler#onCommandReceived(SMSMessage)
     */
    @Test(expected = NullPointerException.class)
    public void onNullCommandReceived() {
        handler.onCommandReceived(null);
    }

    /**
     * Test what happens if there's an attempt to call {@link GPSCommandHandler#sendLocation(SMSPeer, String)}
     * with a null {@link SMSPeer}.
     * <p>
     * {@link NullPointerException} is expected to be thrown, because of the @NonNull annotation.
     *
     * @see NullPointerException
     * @see com.eis.smslibrary.SMSPeer
     * @see GPSCommandHandler#sendLocation(SMSPeer, String)
     */
    @Test(expected = NullPointerException.class)
    public void sendLocationToNullPeer() {
        handler.sendLocation(null, LOCATION);
    }

    /**
     * Test what happens when a certain {@link SMSMessage}, supposed to be a command, is received by
     * method {@link GPSCommandHandler#onCommandReceived(SMSMessage)}.
     *
     * @see com.eis.smslibrary.SMSMessage
     * @see GPSCommandHandler#onCommandReceived(SMSMessage)
     */
    @Test
    public void onCommandReceived() {
        handler.onCommandReceived(smsMessage);
    }

    /**
     * Test method {@link GPSCommandHandler#sendLocation(SMSPeer, String)}.
     *
     * @see com.eis.smslibrary.SMSPeer
     * @see GPSCommandHandler#sendLocation(SMSPeer, String)
     */
    @Test
    public void sendLocation() {
        handler.sendLocation(smsPeer, LOCATION);
    }

}