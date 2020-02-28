package com.example.antitheft;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.antitheft.permissions.PermissionsHandler;

/**
 * Class used to inform the user of which permissions the app needed and requires them
 *
 * @author Francesco Bau', helped by Alberto Ursino.
 * @since 27/02/2020
 */
public class PermissionsActivity extends AppCompatActivity {

    /**
     * Necessary permissions: SEND_SMS, RECEIVE_SMS,
     * ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, ACCESS_BACKGROUND_LOCATION
     */
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        Button permissionsButton = findViewById(R.id.request_permissions_button);
        permissionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] requiredPermissions = PermissionsHandler.getDeniedPermissions(getApplicationContext(), PERMISSIONS);
                //Maybe all the permissions are already granted
                if (requiredPermissions.length != 0)
                    requestPermissions(PermissionsHandler.getDeniedPermissions(getApplicationContext(), PERMISSIONS), PermissionsHandler.REQUEST_CODE);
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_permissions_already_granted), Toast.LENGTH_SHORT).show();
                    changeActivity();
                }
            }
        });

    }

    /**
     * Callback for the permissions request.
     * If the user has not granted all the permissions he cannot continue and a toast will be displayed telling that the app needs the permissions, otherwise calls {@link #changeActivity()}.
     * If the user has selected the option "Don't show it again" must go to the app settings and give them.
     *
     * @param requestCode  has to be the same as {@link PermissionsHandler#REQUEST_CODE}
     * @param permissions  The permissions requested by the {@link Activity#requestPermissions(String[], int)}. Never null.
     * @param grantResults Result of this callback: 0 -> Permissions GRANTED; -1 -> Permissions NOT GRANTED
     *                     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *                     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean check = true;

        final int NOT_GRANTED = -1;

        if (requestCode == PermissionsHandler.REQUEST_CODE) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == NOT_GRANTED) {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_app_needs_permissions), Toast.LENGTH_SHORT).show();
                    check = false;
                    break;
                }
            }

            if (check)
                changeActivity();
        }
    }

    /**
     * Starts the {@link MainActivity}.
     * This activity will then be closed.
     */
    public void changeActivity() {
        Intent nextActivity;
        //Start the MainActivity
        nextActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(nextActivity);
        //This activity will no longer be necessary
        this.finish();

    }

}