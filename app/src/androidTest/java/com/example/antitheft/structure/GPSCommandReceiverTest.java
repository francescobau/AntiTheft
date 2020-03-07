package com.example.antitheft.structure;

import android.util.Log;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

import org.junit.Before;
import org.junit.Test;

import static com.example.antitheft.MainActivity.DEFAULT_COMMAND_PREFIX;
import static com.example.antitheft.structure.LocateCommandHandlerTest.TEXT_MESSAGE;
import static com.example.antitheft.structure.LocateCommandHandlerTest.VALID_TELEPHONE_NUMBER;

/**
 * Test of {@link GPSCommandReceiver} class.
 *
 * @author Francesco Bau'
 * @version 1.1
 * @see GPSCommandReceiver
 * @since 29/02/2020
 */
public class GPSCommandReceiverTest {
    private SMSMessage smsMessage;
    private SMSPeer smsPeer;

    private GPSCommandReceiver receiver;

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
        receiver = new GPSCommandReceiver();
    }

    /**
     * Tests what happens if the listener receives a null {@link SMSMessage}.
     * Nothing bad should happen, since it's @Nullable, and method
     * {@link GPSCommandReceiver#onMessageReceived(SMSMessage)} is supposed to completely ignore
     * null {@link SMSMessage} parameters.
     *
     * @see com.eis.smslibrary.SMSMessage
     * @see GPSCommandReceiver#onMessageReceived(SMSMessage)
     */
    @Test
    public void onNullMessageReceived() {
        receiver.onMessageReceived(null);
    }

    /**
     * Tests what happens if the listener receives a certain {@link SMSMessage}.
     *
     * @see com.eis.smslibrary.SMSMessage
     * @see GPSCommandReceiver#onMessageReceived(SMSMessage)
     */
    @Test
    public void onMessageReceived() {
        receiver.onMessageReceived(smsMessage);
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
        SMSMessage message = new SMSMessage(smsPeer, DEFAULT_COMMAND_PREFIX);
        receiver.onMessageReceived(message);
    }
}