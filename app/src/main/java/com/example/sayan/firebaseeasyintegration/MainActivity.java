package com.example.sayan.firebaseeasyintegration;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sayan.firebaseeasyintegration.firebase.FirebaseListener;
import com.example.sayan.firebaseeasyintegration.firebase.MyFirebaseInstanceIDService;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyFirebaseInstanceIDService.bindListener(new FirebaseListener() {
            @Override
            public void onTokenCreated(final String token) {
                //firebase token is created now
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "token: " + token, Toast.LENGTH_SHORT).show();
                        doNextOperation("1");
                    }
                });
            }
        });

        doFirstRunStuff();
    }

    //runType: 1 means 1st time run (install), 2 means 2nd time run
    private void doNextOperation(String runType) {
        if (runType.equals("1")){
            //first time
        }else {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Toast.makeText(this, "token: " +refreshedToken, Toast.LENGTH_SHORT).show();
        }
    }

    private void doFirstRunStuff() {
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName() + "first_run", MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("first_run", true)) {
            doNextOperation("2");
        } else {
            // Do first run stuff here
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("first_run", false).apply();
        }
    }
}