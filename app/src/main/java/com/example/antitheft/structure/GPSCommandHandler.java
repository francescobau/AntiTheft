package com.example.antitheft.structure;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

/**
 * @author Francesco Bau'
 * @version 0.1
 * <p>
 * This class is used to send commands.
 * @since 25/02/2020
 */
public class GPSCommandHandler {

    //**********SENDING**********

    /**
     * //TODO
     *
     * @param smsMessage
     */
    public void sendCommand(SMSMessage smsMessage) {
        //TODO
    }

    //**********RECEPTION AND REPLY**********

    /**
     * //TODO
     *
     * @param smsMessage
     */
    //Questo metodo verrà chiamato dalla classe GPSCommandReceiver
    public void onCommandReceived(SMSMessage smsMessage) {
        SMSPeer smsPeer = smsMessage.getPeer();
        String currentLocation = getCurrentLocation();

        sendLocation(smsPeer, currentLocation);
    }

    /**
     * @return the current location in String form
     */
    private String getCurrentLocation() {
        //TODO
        return null;
    }

    /**
     * Method used to send our location to the peer who sent us the request.
     * The location is sent through SMS using the {@link com.eis.smslibrary.SMSManager} class.
     *
     * @param smsPeer  The peer who sent us the request
     * @param location Current position of our device
     */
    public void sendLocation(SMSPeer smsPeer, String location) {
        //TODO
    }

}
