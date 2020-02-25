package com.example.antitheft.structure;

import android.location.Location;

import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;

/**
 * //TODO
 */
public class GPSCommandHandler {

    //**********SENDING**********
    /**
     * //TODO
     *
     * @param smsMessage
     */
    public void sendCommand(SMSMessage smsMessage){
        //TODO
    }

    //**********RECEPTION AND REPLY**********

    /**
     * //TODO
     *
     * @param smsMessage
     */
    //Questo metodo verrà chiamato dalla classe GPSCommandReceiver
    public void onCommandReceived(SMSMessage smsMessage){
        SMSPeer smsPeer = smsMessage.getPeer(); //Peer a cui dobbiamo inviare la nostra posizione attuale
        Location location = new Location(GPSLocation.getCurrentLocation()); //La nostra posizione attuale

        GPSLocation gpsLocation = new GPSLocation(smsPeer, location);
        sendLocation(gpsLocation);
    }

    /**
     * //TODO
     *
     * @param gpsLocation
     */
    public void sendLocation(GPSLocation gpsLocation){
        //TODO: 1) Trasformare la gpsLocation in un SMSMessage(parseLocation) 2) Inviare l'SMSMessage
    }

    /**
     * //TODO
     *
     * @param location
     * @return
     */
    public SMSMessage parseLocation(GPSLocation location){
        //TODO
        return null;
    }

}
