package ml.diony.motg.Authentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ml.diony.motg.BuildConfig;
import ml.diony.motg.R;

/**
 * Created by nayak on 2016-12-03.
 */

public class Screen extends Activity implements View.OnClickListener {

    protected String TAG = "MOTGAuth_Scr";

    private Context CONTEXT;

    private ml.diony.motg.init ACTIVITY;

    private int[] lBtID = {0, 0, 0}; //Naver, Facebook, Dummy

    protected boolean[] isLgn = {false, false, false}; //Naver, Facebook, Dummy

    private byte lsLgM = 0; //1: Naver, 2: Facebook, 3: Dummy

    public Screen(Context CONTEXT) {

        Log.i(TAG, "Auth Screen Constructed with Context.");

        this.CONTEXT = CONTEXT;

        //Load Is Logined

        //lsLgM = ~;

        isLgn[0] = (new TypeNaver(CONTEXT)).isLogined();
        isLgn[1] = false;
        isLgn[2] = (lsLgM == 3) && (new TypeDummy()).isLogined();

    }

    public Screen() {

        Log.i(TAG, "Auth Screen Constructed.");

        this.CONTEXT = this;

        //Load Is Logined

        //lsLgM = ~;

        isLgn[0] = (new TypeNaver(CONTEXT)).isLogined();
        isLgn[1] = false;
        isLgn[2] = (lsLgM == 3) && (new TypeDummy()).isLogined();

    }

    public static final int getColor(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return android.support.v4.content.ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public static final Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return android.support.v4.content.ContextCompat.getDrawable(context, id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    private RelativeLayout makeLoginButton(Activity A, int background, int symbol, int color, int message, RelativeLayout.LayoutParams buttonParams) {

        Log.i(TAG, "A Login Button is created.");

        RelativeLayout I = new RelativeLayout(A);

        I.setLayoutParams(buttonParams);
        I.setId(View.generateViewId());
        I.setBackground(getDrawable(A, background));

        ImageView Logo = new ImageView(A);
        Logo.setAdjustViewBounds(true);
        Logo.setId(View.generateViewId());
        Logo.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Logo.setImageResource(symbol);

        RelativeLayout.LayoutParams bdParams = new RelativeLayout.LayoutParams(2, (int) dp2px(A, 6));
        bdParams.addRule(RelativeLayout.RIGHT_OF, Logo.getId());

        RelativeLayout Border = new RelativeLayout(A);
        Border.setLayoutParams(bdParams);
        Border.setId(View.generateViewId());
        Border.setBackgroundColor(getColor(A, R.color.semi_transparent_white));

        RelativeLayout.LayoutParams msgParams = new RelativeLayout.LayoutParams((int) dp2px(A, 34), (int) dp2px(A, 6));
        msgParams.addRule(RelativeLayout.RIGHT_OF, Border.getId());

        TextView Message = new TextView(A);
        Message.setLayoutParams(msgParams);
        Message.setText(message);
        Message.setId(View.generateViewId());
        Message.setTextColor(getColor(A, color));
        Message.setTextSize(21);
        Message.setGravity(Gravity.CENTER);

        I.addView(Logo);
        I.addView(Border);
        I.addView(Message);

        return I;

    }

    private RelativeLayout makeLoginButton(Activity A, int background, int symbol, int color, int message) {

        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams((int) dp2px(A, 40), (int) dp2px(A, 6));//ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(40, 5, 40, 5);

        return makeLoginButton(A, background, symbol, color, message, buttonParams);

    }

    private RelativeLayout makeLoginButton(Activity A, int background, int symbol, int color, int message, RelativeLayout below) {

        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams((int) dp2px(A, 40), (int) dp2px(A, 6));//ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(40, 20, 40, 20);
        buttonParams.addRule(RelativeLayout.ABOVE, below.getId());

        return makeLoginButton(A, background, symbol, color, message, buttonParams);

    }

    private RelativeLayout makeLoginButton(Activity A, int background, int color, int message, RelativeLayout below) {

        Log.i(TAG, "A Login Button is created.");

        RelativeLayout I = new RelativeLayout(A);

        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams((int) dp2px(A, 40), (int) dp2px(A, 6));//ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(40, 5, 40, 5);
        buttonParams.addRule(RelativeLayout.ABOVE, below.getId());

        I.setLayoutParams(buttonParams);
        I.setId(View.generateViewId());
        I.setBackground(getDrawable(A, background));

        RelativeLayout.LayoutParams msgParams = new RelativeLayout.LayoutParams((int) dp2px(A, 40), (int) dp2px(A, 6));

        TextView Message = new TextView(A);
        Message.setLayoutParams(msgParams);
        Message.setText(message);
        Message.setId(View.generateViewId());
        Message.setTextColor(getColor(A, color));
        Message.setTextSize(21);
        Message.setGravity(Gravity.CENTER);

        I.addView(Message);

        return I;

    }

    public static final float dp2px(Context context, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10 * dp, context.getResources().getDisplayMetrics());
    }

    public void authScreenView(ml.diony.motg.init A, Context C) {

        Log.i(TAG, "Auth Screen Started.");

        CONTEXT = C;
        ACTIVITY = A;

        //Make Relative Layout

        RelativeLayout authScreen = new RelativeLayout(A);
        authScreen.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        RelativeLayout authBox = new RelativeLayout(A);
        authBox.setId(View.generateViewId());
        authBox.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        authBox.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        authBox.setBackgroundColor(getColor(A, R.color.semi_transparent_black));
        authBox.setPadding(0, 0, 0, 0 + ((android.os.Build.VERSION.SDK_INT > 20) ? (int) dp2px(A, 6) : 0));

        RelativeLayout logoBox = new RelativeLayout(A);
        logoBox.setId(View.generateViewId());
        logoBox.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        logoBox.setGravity(Gravity.CENTER);
        logoBox.setPadding(0, (int) dp2px(A, 14), 0, 0);

        //Make Logo Image

        ImageView logo = new ImageView(A);
        logo.setLayoutParams(new RelativeLayout.LayoutParams((int) dp2px(A, 26), (int) dp2px(A, 14)));
        logo.setId(View.generateViewId());
        logo.setAdjustViewBounds(true);
        logo.setScaleType(ImageView.ScaleType.CENTER_CROP);
        logo.setImageResource(R.drawable.id_motg);

        //Make DIONYSOURCE Image

        ImageView CI = new ImageView(A);
        CI.setLayoutParams(new RelativeLayout.LayoutParams((int) dp2px(A, 5), (int) dp2px(A, 1)));
        CI.setId(View.generateViewId());
        CI.setAdjustViewBounds(true);
        CI.setScaleType(ImageView.ScaleType.CENTER_CROP);
        CI.setImageResource(R.drawable.id_diony);
        CI.setPadding((int) dp2px(A, 21), (int) dp2px(A, 6), 0, 0);

        //Make Background Image

        ImageView background = new ImageView(A);
        background.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        background.setImageResource(R.drawable.splash_new_back_small);
        background.setScaleType(ImageView.ScaleType.CENTER_CROP);

        //Make Version Information

        TextView version = new TextView(A);
        version.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        version.setText("Euphoria " + BuildConfig.VERSION_NAME);
        version.setTextColor(getColor(A, R.color.white));
        version.setTextSize(13);
        version.setPadding(10, 10 + ((android.os.Build.VERSION.SDK_INT > 20) ? (int) dp2px(A, 2) : 0), 10, 10);

        //Make Naver Login Button

        RelativeLayout naverButton = makeLoginButton(A, R.drawable.lb_naver, R.drawable.ic_naver, R.color.white, R.string.naver_login);
        naverButton.setId(View.generateViewId());
        lBtID[0] = naverButton.getId();
        naverButton.setOnClickListener(this);

        //Make Facebook Login Button

        RelativeLayout facebookButton = makeLoginButton(A, R.drawable.lb_facebook, R.drawable.ic_facebook, R.color.white, R.string.facebook_login, naverButton);
        facebookButton.setId(View.generateViewId());
        lBtID[1] = facebookButton.getId();
        facebookButton.setOnClickListener(this);

        //Make Dummy Login Button
        RelativeLayout dummyButton = makeLoginButton(A, R.drawable.lb_dummy, R.color.dummy_text, R.string.dummy_login, facebookButton);
        dummyButton.setId(View.generateViewId());
        lBtID[2] = dummyButton.getId();
        dummyButton.setOnClickListener(this);

        //Add Buttons and Display

        authBox.addView(naverButton);
        authBox.addView(facebookButton);
        authBox.addView(dummyButton);

        logoBox.addView(logo);
        logoBox.addView(CI);

        authScreen.addView(background);
        authScreen.addView(version);
        authScreen.addView(authBox);
        authScreen.addView(logoBox);

        A.setContentView(authScreen);

    }

    @Override
    public void onClick(View v) {

        Log.i(TAG, "Click detected.");

        if (v.getId() == lBtID[0]) {

            Log.i(TAG, "Naver Button Clicked.");

            //lastLoginMethod = 1;

            Intent loginIntent = new Intent(CONTEXT, TypeNaver.class);
            CONTEXT.startActivity(loginIntent);

            lsLgM = 1;

        } else if (v.getId() == lBtID[1]) {

            Log.i(TAG, "Facebook Button Clicked.");

            //lastLoginMethod = 2;

            //Intent loginIntent = new Intent(A, naverLogin.class);
            //startActivity(loginIntent);

            lsLgM = 2;

        } else if (v.getId() == lBtID[2]) {

            Log.i(TAG, "Dummy Login Button Clicked.");

            //lastLoginMethod = 3;

            TypeDummy loginIntent = new TypeDummy();

            loginIntent.procLogin();

            lsLgM = 3;

        }

        Log.i(TAG + "_btnLsr", isLogined() ? "Login Succeed" : "Login Failed");

        if (isLogined()) {

            ACTIVITY.checkUTD();

        }

    }

    public boolean isLogined() {

        isLgn[0] = (new TypeNaver(CONTEXT)).isLogined();
        isLgn[1] = false;
        isLgn[2] = (lsLgM == 3) && (new TypeDummy()).isLogined();

        return (isLgn[0] || isLgn[1] || isLgn[2]);

    }

}
