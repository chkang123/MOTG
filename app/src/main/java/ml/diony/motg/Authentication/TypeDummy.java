package ml.diony.motg.Authentication;

import android.os.Bundle;
import android.util.Log;

/**
 * Created by nayak on 2016-12-03.
 */

public class TypeDummy extends Base {

    public TypeDummy() {
    }

    public boolean procLogin() {

        Log.i(TAG + "_dm", "Login Processed.");

        return true;

    }

    public boolean isLogined() {

        return true;

    }

    @Override
    public void onCreate(Bundle s) {

        super.onCreate(s);

    }

}
