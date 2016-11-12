package io.dionysource.motg.auth;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLoginHandler;

/**
 * Created by nayak on 2016-11-06.
 */

public class naverLogin extends naverMaster {

    private static String NAVER_ERROR_CODE, NAVER_ERROR_DESCRIPTION;

    private OAuthLoginHandler AuthHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean b) {
            if(b) {

                //Login Success

                Log.i(TAG + "_nv_login", "Login succeeded!");

                Toast.makeText (getApplicationContext(), authInstance.getAccessToken(authContext), Toast.LENGTH_LONG).show();

                NAVER_AC_TOKEN = authInstance.getAccessToken(authContext);
                NAVER_RF_TOKEN = authInstance.getRefreshToken(authContext);
                NAVER_EXP = authInstance.getExpiresAt(authContext);
                NAVER_TOKEN_TYPE = authInstance.getTokenType(authContext);

                Log.i(TAG + "_nv_login", authInstance.getState(authContext).toString());

                finish();

            } else {

                Log.i(TAG + "_nv_login", "Failed to login!");

                Toast.makeText (getApplicationContext(), "로그인에 실패했습니다. 다시 로그인해주세요.", Toast.LENGTH_LONG).show();

                NAVER_ERROR_CODE = authInstance.getLastErrorCode(authContext).getCode();
                NAVER_ERROR_DESCRIPTION = authInstance.getLastErrorDesc(authContext);

                Log.i(TAG + "_nv_login", "CODE: " + NAVER_ERROR_CODE);
                Log.i(TAG + "_nv_login", "DESC: " + NAVER_ERROR_DESCRIPTION);

                finish();

            }
        }
    };

    @Override
    public void onCreate(Bundle s) {

        Log.i(TAG + "_nv_login", "Naver Login Activity created.");

        super.onCreate(s);

        authContext = this;
        setAuthInformation();

        Log.i(TAG + "_nv_login", "OauthLoginActivity starts.");

        authInstance.startOauthLoginActivity(this, AuthHandler);

    }

}
