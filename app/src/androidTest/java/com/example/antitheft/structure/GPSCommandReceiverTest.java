package com.example.antitheft.structure;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

import org.junit.Before;
import org.junit.Test;

import static com.example.antitheft.MainActivity.FULL_DEFAULT_LOCATE_COMMAND;
import static com.example.antitheft.structure.GPSCommandHandlerTest.TEXT_MESSAGE;
import static com.example.antitheft.structure.GPSCommandHandlerTest.VALID_TELEPHONE_NUMBER;

/**
 * Test of {@link GPSCommandReceiver} class.
 *
 * @author Francesco Bau'
 * @see GPSCommandReceiver
 * @since 29/02/2020
 */
public class GPSCommandReceiverTest {
    private SMSMessage smsMessage;
    private SMSPeer smsPeer;

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
    }

    /**
     * Tests what happens if the listener receives a null message.
     * Nothing bad should happen, since it's @Nullable, and method
     * {@link GPSCommandReceiver#onMessageReceived(SMSMessage)} is supposed to completely ignore
     * null {@link SMSMessage} parameters.
     *
     * @see com.eis.smslibrary.SMSMessage
     * @see GPSCommandReceiver#onMessageReceived(SMSMessage)
     */
    @Test
    public void onNullMessageReceived() {
        new GPSCommandReceiver().onMessageReceived(null);
    }

    /**
     * Tests what happends if the listener receives a certain {@link SMSMessage}.
     *
     * @see com.eis.smslibrary.SMSMessage
     * @see GPSCommandReceiver#onMessageReceived(SMSMessage)
     */
    @Test
    public void onMessageReceived() {
        new GPSCommandReceiver().onMessageReceived(smsMessage);
    }

    /**
     * Almost the same as method {@link #onMessageReceived()}, but it tests what
     * happens if a valid LOCATE command is received.
     *
     * @see com.eis.smslibrary.SMSMessage
     * @see GPSCommandReceiver#onMessageReceived(SMSMessage)
     */
    @Test
    public void onValidLocateCommandReceived() {
        SMSMessage message = new SMSMessage(smsPeer, FULL_DEFAULT_LOCATE_COMMAND);
        new GPSCommandReceiver().onMessageReceived(message);
    }
}