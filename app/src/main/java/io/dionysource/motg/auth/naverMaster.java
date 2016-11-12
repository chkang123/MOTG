package io.dionysource.motg.auth;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

    public naverMaster(Context CXT) {

        Log.i(TAG + "_nv", "Naver Master Constructed with Context.");

        this.authContext = CXT;

    }

    public naverMaster() {

        Log.i(TAG + "_nv", "Naver Master Constructed.");

    }

    @Override
    public void onCreate(Bundle savedBundleInstance) {

        Log.i(TAG + "_nv", "Naver Master Started.");

        super.onCreate(savedBundleInstance);

        this.setTitle("MOTG Naver Login");

        OAuthLoginDefine.DEVELOPER_VERSION = isDeveloperVersion;

    }

    @Override
    public void onResume() {

        Log.i(TAG + "_nv", "Naver Master Resumed.");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        super.onResume();

    }

    protected static void setAuthInformation() {

        Log.i(TAG + "_nv", "Setting Auth Informations.");

        authInstance = OAuthLogin.getInstance();

        Log.i(TAG + "_nv", "Get Auth Instance Succeeded.");

        authInstance.init(authContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);

    }

    public static boolean isLogined() {

        Log.i(TAG + "_nv", "Starts to check if it is logined.");

        //return false;

        setAuthInformation();

        Log.i(TAG + "_nv", "Setting Information Completed.");

        if(authInstance.getState(authContext).toString() == "OK")
            return true;
        else
            return false;

    }

}
