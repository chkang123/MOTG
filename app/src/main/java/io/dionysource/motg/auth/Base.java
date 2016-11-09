package io.dionysource.motg.auth;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by nayak on 2016-11-06.
 */

public class Base extends Activity {

    protected static String TAG = "MOTGAuth";

    protected static boolean isDeveloperVersion = true;

    @Override
    public void onCreate(Bundle s) {

        super.onCreate(s);

        this.setTitle("MOTG Login");

    }

}
