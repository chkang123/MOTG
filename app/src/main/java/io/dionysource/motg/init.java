package io.dionysource.motg;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import io.dionysource.motg.auth.Manager;

/**
 * Created by nayak on 2016-10-16.
 */

final public class init extends Activity {

    protected String TAG = "MOTGInit";

    final Context THIS_CONTEXT = this;
    final Activity THIS_ACTIVITY = this;

    static Manager loginer;

    @Override
    public void onCreate(Bundle s) {

        Log.i(TAG, "Init Activity Created.");

        super.onCreate(s);

        loginer = new io.dionysource.motg.auth.Manager(this);

    }

    @Override
    public void onResume() {

        Log.i(TAG, "Init Activity Resumed.");

        super.onResume();

        if(loginer.isLogined()) {

            //When user succeeded to login.

            Log.i(TAG, "User Login Confirmed.");

            //여기서 Main 화면이 호출된다.

        } else {

            Log.i(TAG, "User Login Needed.");

            loginer.authScreenView(THIS_ACTIVITY, THIS_CONTEXT);

        }

    }

    @Override
    public void onDestroy() {

        //App Exit

        Log.i(TAG, "Init Activity Destroyed.");

        super.onDestroy();

    }

}
