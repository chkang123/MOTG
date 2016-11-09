package io.dionysource.motg.auth;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginDefine;

/**
 * Created by nayak on 2016-11-06.
 */

public class naverMaster extends io.dionysource.motg.auth.Base {

    protected static OAuthLogin   authInstance;
    protected static Context      authContext;

    private static String OAUTH_CLIENT_ID = "ToG7rEcuYkuXNlH92Xr8";
    private static String OAUTH_CLIENT_SECRET = "w8f1vj8t8a";
    private static String OAUTH_CLIENT_NAME = "MOTG - 맛지";

    @Override
    public void onCreate(Bundle savedBundleInstance) {

        super.onCreate(savedBundleInstance);

        this.setTitle("MOTG Naver Login");

        OAuthLoginDefine.DEVELOPER_VERSION = isDeveloperVersion;

    }

    @Override
    public void onResume() {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        super.onResume();

    }

    protected void setAuthInformation() {

        authInstance = OAuthLogin.getInstance();
        authInstance.init(authContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);

    }

    public static boolean isLogined() {

        return false;

    };

}
