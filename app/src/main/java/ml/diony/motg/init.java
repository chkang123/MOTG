package ml.diony.motg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import ml.diony.motg.Authentication.Base;
import ml.diony.motg.Authentication.Screen;
import ml.diony.motg.Communication.Sync;

/**
 * Created by nayak on 2016-12-03.
 */

final public class init extends Activity {

    static public Screen S;
    static public Base B;
    static Sync C;
    final Context CONTEXT = this;
    final Activity ACTIVITY = this;
    protected String TAG = "MOTGInit";
    protected boolean cLogin = false, cUTD = false, cInit = false;

    public init() {}

    //init Activity가 생성될 때, 로그인 처리와 서버 통신에 필요한 데이터들을 초기화한다.
    @Override
    public void onCreate(Bundle s) {

        Log.i(TAG, "Init Activity Created.");

        super.onCreate(s);

        S = new ml.diony.motg.Authentication.Screen(this);
        C = new ml.diony.motg.Communication.Sync(getFilesDir(), CONTEXT, ACTIVITY, this);
        B = new Base();
        JSONObject X = B.getLoginInformation();

        if (X != null) {

            Log.i(TAG, "Check is " + X.toString());

            try {

                Log.i(TAG, "TEST START");

                Log.i(TAG + "c32", X.getString("TYPE"));

                if (X.getString("TYPE").indexOf("guest") == 0) {

                    Log.i(TAG, "SETTED TO GUEST");

                    S.setLSLGM((byte) 3);

                    S.setId(X.getString("ID"));

                }

            } catch (Exception I) {
            }
        }

    }

    //onResume에서는 로그인 여부를 체크한다. 로그인이 되어 있지 않으면 로그인 화면을 띄우고, 아니면 Update를 시도한다.
    @Override
    public void onResume() {

        Log.i(TAG, "Init Activity Resumed.");

        super.onResume();

        if (cLogin || S.isLogined()) {

            Log.i(TAG, "User Login Confirmed.");

            B.saveLoginInformation(S.checkId().getId(), S.getType());

            //DATA LOADING SCREEN CALLED.

            cLogin = true;

            checkUTD();


        } else {

            Log.i(TAG, "User Login Needed.");

            S.authScreenView(this, CONTEXT);

        }

    }

    @Override
    public void onDestroy() {

        //App Exit

        Log.i(TAG, "Init Activity Destroyed.");

        super.onDestroy();

    }

    //checkUTD에서는 Application의 모든 데이터가 Up-To-Date인지 확인한다.
    public void checkUTD() {

        Log.i(TAG, "Starts to check is this application UP-TO-DATE.");

        if (cUTD) {

            //Version Check Is Not Needed. Directly Goes to main screen.

            Log.i(TAG, "This application is UP-TO-DATE.");

            if (cInit) {

                this.finishAffinity();

            } else {

                cInit = true;

            }

        } else {

            cUTD = true;

            cInit = true;

            Log.i(TAG, "This application needs to UPDATE.");

            Intent CI = new Intent(this, Sync.class);

            startActivity(CI);


            //C.accountSync();

        }


    }

}
