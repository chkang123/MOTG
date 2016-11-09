package io.dionysource.motg.auth;

import android.os.Bundle;

/**
 * Created by nayak on 2016-11-06.
 */

public class naverLogin extends naverMaster {

    @Override
    public void onCreate(Bundle s) {

        super.onCreate(s);

        authContext = this;
        setAuthInformation();

        

    }

}
