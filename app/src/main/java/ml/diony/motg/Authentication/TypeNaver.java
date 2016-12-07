package ml.diony.motg.Authentication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//Copyright 2017 YUOA.

public class TypeNaver extends Base {

    protected static OAuthLogin authInstance;
    protected static Context authContext;
    protected static String NAVER_AC_TOKEN, NAVER_RF_TOKEN, NAVER_TOKEN_TYPE;
    protected static long NAVER_EXP;
    private static String OAUTH_CLIENT_ID = "ToG7rEcuYkuXNlH92Xr8";
    private static String OAUTH_CLIENT_SECRET = "w8f1vj8t8a";
    private static String OAUTH_CLIENT_NAME = "MOTG - 맛지";
    private static String NAVER_ERROR_CODE, NAVER_ERROR_DESCRIPTION;
    protected String ID = "";

    //로그인을 핸들링하는 HANDLER이다. 로그인 이후 실행되는 코드들이다.
    private OAuthLoginHandler AuthHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean b) {
            if (b) {

                //Login Success

                Log.i(TAG + "_nv_login", "Login succeeded!");

                //Toast.makeText(getApplicationContext(), authInstance.getAccessToken(authContext), Toast.LENGTH_LONG).show();

                NAVER_AC_TOKEN = authInstance.getAccessToken(authContext);
                NAVER_RF_TOKEN = authInstance.getRefreshToken(authContext);
                NAVER_EXP = authInstance.getExpiresAt(authContext);
                NAVER_TOKEN_TYPE = authInstance.getTokenType(authContext);

                Log.i(TAG + "_nv_login", authInstance.getState(authContext).toString());

                finish();

            } else {

                Log.i(TAG + "_nv_login", "Failed to login!");
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

    public TypeNaver(Context CONTEXT) {

        Log.i(TAG + "_nv", "TypeNaver Constructed with Context.");

        authContext = CONTEXT;

    }

    public TypeNaver() {

        Log.i(TAG + "_nv", "TypeNaver Constructed.");

        authContext = this;

    }

    //로그인 시도에 필요한 기본적인 데이터를 설정하는 함수이다.
    protected static void setAuthInformation() {

        Log.i(TAG + "_nv", "Setting Auth Informations.");

        authInstance = OAuthLogin.getInstance();

        Log.i(TAG + "_nv", "Get Auth Instance Succeeded.");

        Log.i(TAG + "_nv", "Is authContext NULL? " + (authContext.getPackageName() == null));

        authInstance.init(authContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);

    }

    //네이버 로그인 여부를 확인하는 함수이다.
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

    //TypeNaver Activity는 실행되자마자 login을 시도한다.
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

    //로그인을 시도하는 함수이다.
    public void procLogin() {

        setAuthInformation();

        Log.i(TAG + "_nv", "OAuthLoginActivity starts.");

        authInstance.startOauthLoginActivity(this, AuthHandler);

    }

    //NAVER LOGIN의 ID를 얻어오는 함수이다.
    public String getID() {

        setAuthInformation();

        Log.i("WHATISNAMVE", "NMVVE");

        try {
            (new Thread() {

                @Override
                public void run() {

                    String token = authInstance.getAccessToken(authContext);// 네이버 로그인 접근 토큰;
                    String header = "Bearer " + token; // Bearer 다음에 공백 추가
                    try {
                        String apiURL = "https://openapi.naver.com/v1/nid/me";
                        URL url = new URL(apiURL);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        con.setRequestProperty("Authorization", header);
                        Log.i("NAVERLOGIN", "리스폰 요청?");
                        int responseCode = con.getResponseCode();
                        Log.i("NAVERLOGIN", "리스폰 요청!");
                        BufferedReader br;
                        if (responseCode == 200) { // 정상 호출
                            Log.i("NAVERLOGIN", "정상 호출!");
                            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        } else {  // 에러 발생
                            Log.i("NAVERLOGIN", "에러발생!");
                            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                        }
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = br.readLine()) != null) {
                            response.append(inputLine);
                        }
                        br.close();
                        Log.i("USERDATA_NV", response.toString());
                        JSONObject X = new JSONObject(response.toString());
                        Log.i("USERDATA_TEST", X.getJSONObject("response").getString("id"));

                        ID = X.getJSONObject("response").getString("id");
                    } catch (Exception e) {

                        Log.i("에러라니..", e.toString());
                    }

                }

            }).start();
        } finally {


            return authInstance.getAccessToken(authContext);

        }

    }

}
