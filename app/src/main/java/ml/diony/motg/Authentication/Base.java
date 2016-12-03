package ml.diony.motg.Authentication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by nayak on 2016-12-03.
 */

public class Base extends Activity {

    protected static String TAG = "MOTGAuth";

    @Override
    public void onCreate(Bundle s) {

        Log.i(TAG, "Communication of Authentication.");

        super.onCreate(s);

        this.setTitle("MOTG Login");

    }

    @Override
    public void onDestroy() {

        super.onDestroy();

    }

}
