package com.example.antitheft;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eis.smslibrary.SMSManager;
import com.eis.smslibrary.SMSPeer;
import com.eis.smslibrary.exceptions.InvalidTelephoneNumberException;
import com.example.antitheft.structure.GPSCommandReceiver;
import com.example.antitheft.structure.GPSLocationManager;
import com.example.antitheft.structure.LocateCommandHandler;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Main Activity. It takes UI commands as input, and, as an output, it gives them a certain action.
 * Its current scope is just to send SMS, based by what does the User decide to do.
 *
 * @author Francesco Bau'
 * @version 1.1
 * @since 24/02/2020
 */
public class MainActivity extends AppCompatActivity {

    EditText telephoneField;
    View sendButton;

    private static final String APP_CODE = "AT";
    private static final int DEFAULT_PASSWORD = 1234;

    // Default command prefix: "AT-1234"
    public static final String DEFAULT_COMMAND_PREFIX = APP_CODE + "-" + DEFAULT_PASSWORD;

    /**
     * Necessary permissions: SEND_SMS, RECEIVE_SMS, WAKE_LOCK,
     * ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, ACCESS_BACKGROUND_LOCATION
     */
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.WAKE_LOCK,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION};

    private static final int REQUEST_CODE = 0;

    private static Activity activity;

    private LocateCommandHandler locateCommandHandler;

    /**
     * Method called when {@link MainActivity} starts.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     * @see AppCompatActivity#onCreate(Bundle)
     * @see androidx.fragment.app.FragmentActivity#onCreate(Bundle)
     * @see androidx.activity.ComponentActivity#onCreate(Bundle)
     * @see androidx.core.app.ComponentActivity#onCreate(Bundle)
     * @see android.app.Activity#onCreate(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        this.locateCommandHandler = new LocateCommandHandler();

        //Setting the listener
        SMSManager.getInstance().setReceivedListener(GPSCommandReceiver.class, activity);

        telephoneField = findViewById(R.id.telephoneNumber);
        sendButton = findViewById(R.id.sendButton);


        /**
         * Setting the {@link View.OnClickListener} for the button.
         * @see View.OnClickListener
         * @see View#setOnClickListener(View.OnClickListener)
         */
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean permissionsGranted = checkSelfPermission(Manifest.permission.SEND_SMS) == PERMISSION_GRANTED;
                if (!permissionsGranted) {
                    // If SMS permissions are not granted, it will show a toast, because
                    // the app can't send commands without that permission.
                    Toast.makeText(activity, R.string.toast_app_needs_SMS_permissions, Toast.LENGTH_SHORT).show();
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, REQUEST_CODE);
                    return;
                }
                // This block will be executed only if SMS permissions are granted.
                String telephoneNumber = ((EditText) findViewById(R.id.telephoneNumber)).getText().toString();
                // Checking the peer.
                if (telephoneNumber == null || telephoneNumber.isEmpty()) {
                    Toast.makeText(activity, R.string.empty_telephone_number, Toast.LENGTH_SHORT).show();
                    return;
                }
                SMSPeer peer;
                // Check if the telephoneNumber is a valid telephone number or not.
                try {
                    peer = new SMSPeer(telephoneNumber);
                    // If everything is OK, it sends the command.
                    locateCommandHandler.sendLocateCommandTo(peer);
                } catch (InvalidTelephoneNumberException e) {
                    Toast.makeText(activity, R.string.invalid_telephone_number, Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e1) {
                    Toast.makeText(activity, R.string.toast_non_valid_peer, Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Request permissions.
        requestPermissions(PERMISSIONS, REQUEST_CODE);

        GPSLocationManager.getCurrentLocation(activity);

    }

    public static Activity getActivity(){
        return activity;
    }
}