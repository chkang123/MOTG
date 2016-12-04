package ml.diony.motg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import ml.diony.motg.Authentication.Screen;

/**
 * Created by nayak on 2016-12-03.
 */

final public class init extends Activity {

    protected String TAG = "MOTGInit";

    protected boolean cLogin = false, cUTD = false, cInit = false;

    final Context CONTEXT = this;
    final Activity ACTIVITY = this;

    static Screen S;
    static Communication C;

    public static final int getColor(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return android.support.v4.content.ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public init() {

        //Plz process cLogin, cUTD



    }

    @Override
    public void onCreate(Bundle s) {

        Log.i(TAG, "Init Activity Created.");

        super.onCreate(s);

        S = new ml.diony.motg.Authentication.Screen(this);
        C = new ml.diony.motg.Communication(getFilesDir(), CONTEXT, ACTIVITY);

    }

    @Override
    public void onResume() {

        Log.i(TAG, "Init Activity Resumed.");

        super.onResume();

        if(cLogin || S.isLogined()) {

            Log.i(TAG, "User Login Confirmed.");

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

    public void checkUTD() {

        Log.i(TAG, "Starts to check is this application UP-TO-DATE.");

        if(cUTD) {

            //Version Check Is Not Needed. Directly Goes to main screen.

            Log.i(TAG, "This application is UP-TO-DATE.");

            if(cInit) {

                this.finishAffinity();

            } else {

                cInit = true;

            }

        } else {

            cUTD = true;

            cInit = true;

            Log.i(TAG, "This application needs to UPDATE.");

            Intent CI = new Intent(this, Communication.class);

            startActivity(CI);


            //C.accountSync();

        }

    }

    public void changeLoadingText(String TXT) {

        final TextView TXV = (TextView) this.findViewById(R.id.ldT);

        TXV.setText(TXT);

    }

}
