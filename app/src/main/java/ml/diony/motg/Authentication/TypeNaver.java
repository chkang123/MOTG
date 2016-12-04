package ml.diony.motg.Authentication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

/**
 * Created by nayak on 2016-12-03.
 */

public class TypeNaver extends Base {

    protected static OAuthLogin authInstance;
    protected static Context authContext;

    private static String OAUTH_CLIENT_ID = "ToG7rEcuYkuXNlH92Xr8";
    private static String OAUTH_CLIENT_SECRET = "w8f1vj8t8a";
    private static String OAUTH_CLIENT_NAME = "MOTG - 맛지";

    private static String NAVER_ERROR_CODE, NAVER_ERROR_DESCRIPTION;

    protected static String NAVER_AC_TOKEN, NAVER_RF_TOKEN, NAVER_TOKEN_TYPE;

    protected static long NAVER_EXP;

    public TypeNaver(Context CONTEXT) {

        Log.i(TAG + "_nv", "TypeNaver Constructed with Context.");

        this.authContext = CONTEXT;

    }

    public TypeNaver() {

        Log.i(TAG + "_nv", "TypeNaver Constructed.");

        this.authContext = this;

    }

    @Override
    public void onCreate(Bundle savedBundleInstance) {

        Log.i(TAG + "_nv", "TypeNaver Started.");

        super.onCreate(savedBundleInstance);

        this.setTitle("MOTG Naver Login");

        //OAuthLoginDefine.DEVELOPER_VERSION = true;

        procLogin();

    }

    @Override
    public void onResume() {

        Log.i(TAG + "_nv", "TypeNaver Resumed.");

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

        setAuthInformation();

        Log.i(TAG + "_nv", "Setting Information Completed.");

        if (authInstance.getState(authContext).toString() == "OK") {

            Log.i(TAG + "_nv", "Login Confirmed.");

            return true;

        } else {

            Log.i(TAG + "_nv", "Login does not Confirmed.");

            return false;

        }

    }

    public void procLogin() {

        setAuthInformation();

        Log.i(TAG + "_nv", "OAuthLoginActivity starts.");

        authInstance.startOauthLoginActivity(this, AuthHandler);

    }

    private OAuthLoginHandler AuthHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean b) {
            if (b) {

                //Login Success

                Log.i(TAG + "_nv_login", "Login succeeded!");

                Toast.makeText(getApplicationContext(), authInstance.getAccessToken(authContext), Toast.LENGTH_LONG).show();

                NAVER_AC_TOKEN = authInstance.getAccessToken(authContext);
                NAVER_RF_TOKEN = authInstance.getRefreshToken(authContext);
                NAVER_EXP = authInstance.getExpiresAt(authContext);
                NAVER_TOKEN_TYPE = authInstance.getTokenType(authContext);

                Log.i(TAG + "_nv_login", authInstance.getState(authContext).toString());

                finish();

            } else {

                Log.i(TAG + "_nv_login", "Failed to login!");

                Toast.makeText(getApplicationContext(), "로그인에 실패했습니다. 다시 로그인해주세요.", Toast.LENGTH_LONG).show();

                NAVER_ERROR_CODE = authInstance.getLastErrorCode(authContext).getCode();
                NAVER_ERROR_DESCRIPTION = authInstance.getLastErrorDesc(authContext);

                Log.i(TAG + "_nv_login", "CODE: " + NAVER_ERROR_CODE);
                Log.i(TAG + "_nv_login", "DESC: " + NAVER_ERROR_DESCRIPTION);

                finish();

            }
        }
    };

}
