package com.example.antitheft;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSMessage;
import com.eis.smslibrary.SMSPeer;
import com.eis.smslibrary.exceptions.InvalidTelephoneNumberException;
import com.example.antitheft.structure.GPSCommandHandler;
import com.example.antitheft.structure.GPSCommandReceiver;
import com.example.antitheft.structure.GPSLocationManager;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Main Activity. It takes UI commands as input, and, as an output, it gives them a certain action.
 * Its current scope is just to send SMS, based by what does the User decide to do.
 *
 * @author Francesco Bau'
 * @version 1.0
 * @since 24/02/2020
 */
public class MainActivity extends AppCompatActivity {

    EditText telephoneField;
    View sendButton;

    private static final String APP_CODE = "AT";
    private static final int DEFAULT_PASSWORD = 1234;

    private static final String LOCATE_COMMAND = "LOCATE";

    // Full Default LOCATE command: "AT-1234 LOCATE"
    public static final String FULL_DEFAULT_LOCATE_COMMAND = APP_CODE + "-" + DEFAULT_PASSWORD + " " + LOCATE_COMMAND;

    /**
     * Necessary permissions: SEND_SMS, RECEIVE_SMS, WAKE_LOCK,
     * ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, ACCESS_BACKGROUND_LOCATION
     */
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.WAKE_LOCK,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION};

    private static final int REQUEST_CODE = 0;

    private GPSLocationManager locationManager;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();

        //Setting the listener
        SMSManager.getInstance().setReceivedListener(GPSCommandReceiver.class, context);

        telephoneField = findViewById(R.id.telephoneNumber);
        sendButton = findViewById(R.id.sendButton);
        //activity = this;

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean permissionsGranted = checkSelfPermission(Manifest.permission.SEND_SMS) == PERMISSION_GRANTED;
                if (!permissionsGranted) {
                    // If SMS permissions are not granted, it will show a toast, because
                    // the app can't send commands without that permission.
                    Toast.makeText(context, R.string.toast_app_needs_SMS_permissions, Toast.LENGTH_SHORT).show();
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, REQUEST_CODE);
                    return;
                }
                // This block will be executed only if SMS permissions are granted.
                String telephoneNumber = ((EditText) findViewById(R.id.telephoneNumber)).getText().toString();
                // Checking the peer.
                if (telephoneNumber == null || telephoneNumber.isEmpty()) {
                    Toast.makeText(context, R.string.empty_telephone_number, Toast.LENGTH_SHORT).show();
                    return;
                }
                SMSPeer peer;
                // Check if the telephoneNumber is a valid telephone number or not.
                try {
                    peer = new SMSPeer(telephoneNumber);
                } catch (InvalidTelephoneNumberException e) {
                    Toast.makeText(context, R.string.invalid_telephone_number, Toast.LENGTH_SHORT).show();
                    return;
                } catch (NullPointerException e1) {
                    Toast.makeText(context, R.string.toast_non_valid_peer, Toast.LENGTH_SHORT).show();
                    return;
                }
                // If everything is OK, it sends the command.
                sendCommand(new SMSMessage(peer, FULL_DEFAULT_LOCATE_COMMAND));
            }
        });
        // Request permissions.
        requestPermissions(PERMISSIONS, REQUEST_CODE);

        /**
         *  When constructor is called, it will retrieve the last known location immediately.
         * @see GPSLocationManager
         */
        locationManager = new GPSLocationManager();

    }

    /**
     * This method sends the command, calling the method in {@link GPSCommandHandler} class, if
     * command is not null.
     *
     * @param smsMessage The message to send.
     * @see com.eis.smslibrary.SMSMessage
     * @see GPSCommandHandler
     * @see GPSCommandHandler#sendMessage(SMSMessage)
     */
    private void sendCommand(@Nullable SMSMessage smsMessage) {
        if (smsMessage != null)
            new GPSCommandHandler().sendMessage(smsMessage);
        else
            Toast.makeText(this, R.string.sendCommand_fail, Toast.LENGTH_SHORT).show();
    }

}