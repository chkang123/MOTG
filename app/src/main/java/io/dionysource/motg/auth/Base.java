package io.dionysource.motg.auth;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by nayak on 2016-11-06.
 */

public class Base extends Activity {

    protected static String TAG = "MOTGAuth";

    protected static boolean isDeveloperVersion = true;

    protected static String NAVER_AC_TOKEN, NAVER_RF_TOKEN, NAVER_TOKEN_TYPE;

    protected static long NAVER_EXP;

    protected void saveKeyInfo() {

        Log.i(TAG, "Starts to save Login Key to Database.");

        Log.i(TAG, "Saving the key is completed.");

    }

    protected boolean getKeyInfo() {

        Log.i(TAG, "Tries to get Login Key from Database.");

        return true; //If success = true.

    }

    @Override
    public void onCreate(Bundle s) {

        Log.i(TAG, "Auth base created.");

        super.onCreate(s);

        getKeyInfo();

        this.setTitle("MOTG Login");

    }

    @Override
    public void onDestroy() {

        saveKeyInfo();

        super.onDestroy();

    }

}
