package io.dionysource.motg;

import android.app.Activity;
import android.os.Bundle;

import io.dionysource.motg.auth.Manager;

/**
 * Created by nayak on 2016-10-16.
 */

public class init extends Activity {

    Manager loginer;

    @Override
    public void onCreate(Bundle s) {

        super.onCreate(s);

        loginer = new io.dionysource.motg.auth.Manager();

        if(loginer.isLogined()) {

            //When user logined.

        } else
            loginer.authScreenView(this);

    }

    @Override
    public void onResume() {

        if(loginer.isLogined()) {

            //When user succeeded to login.

        } else
            loginer.authScreenView(this);

        super.onResume();

    }

    @Override
    public void onDestroy() {

        //App Exit

        super.onDestroy();

    }

}
