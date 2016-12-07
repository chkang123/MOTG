package ml.diony.motg.Authentication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.SecureRandom;

import ml.diony.motg.BuildConfig;
import ml.diony.motg.R;

//Copyright 2017 YUOA.

public class Screen extends Activity implements View.OnClickListener {

    protected String TAG = "MOTGAuth_Scr";
    protected boolean[] isLgn = {false, false, false}; //Naver, Facebook, Dummy
    private Context CONTEXT;
    private ml.diony.motg.init ACTIVITY;
    private int[] lBtID = {0, 0, 0}; //Naver, Facebook, Dummy
    private byte lsLgM = 0; //1: Naver, 2: Facebook, 3: Dummy

    private String ID = "";

    //Constructor에서는 기본적인 로그인 정보를 불러온다.
    public Screen(Context CONTEXT) {

        Log.i(TAG, "Auth Screen Constructed with Context.");

        this.CONTEXT = CONTEXT;

        isLgn[0] = (new TypeNaver(CONTEXT)).isLogined();
        isLgn[1] = false;
        isLgn[2] = (lsLgM == 3) && (new TypeDummy()).isLogined();

        if (this.isLogined()) {

            if (isLgn[2] == true) {

                try {

                    ID = (new Base()).getLoginInformation().getString("ID");

                } catch (Exception E) {
                }

            } else if (isLgn[0] == true) {

                ID = (new TypeNaver(CONTEXT)).getID();

            } else if (isLgn[1] == true) {

                //FACEBOOK

            }

        }

    }

    //color를 불러오는 함수이다.
    public static final int getColor(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return android.support.v4.content.ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    //drawable을 불러오는 함수이다.
    public static final Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return android.support.v4.content.ContextCompat.getDrawable(context, id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    //dp를 px로 바꾸는 함수이다. 코드 상에서는 dip 단위를 쓸 수 없기에 필요하다.
    public static final float dp2px(Context context, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10 * dp, context.getResources().getDisplayMetrics());
    }

    //ID를 불러오는 함수이다.
    public String getId() {

        return ID;

    }

    //Thread에서 ID를 쓸 때 사용하는 함수이다.
    public void setId(String ID) {

        this.ID = ID;

    }

    //각 최근 로그인 METHOD에 맞게 ID를 불러오는 함수이다.
    public Screen checkId() {

        if (isLgn[2]) {

            if (ID == "") {

                Log.i(TAG, "is RANDOM!");

                SecureRandom RND = new SecureRandom();
                ID = (new BigInteger(130, RND)).toString();

            }

        } else if (isLgn[1])
            Log.i(TAG, "FACEBOOK?");
        else if (isLgn[0]) {

            Handler mHandler = new Handler(Looper.getMainLooper());

            mHandler.postDelayed(new Runnable() {

                @Override

                public void run() {

                    ID = (new TypeNaver(CONTEXT)).getID();

                }

            }, 0);

            Log.i(TAG, "GOOD!!");

        }

        return this;

    }

    //최근 로그인 METHOD를 저장하는 함수이다.
    public void setLSLGM(byte X) {
        lsLgM = X;
    }

    public String getType() {

        if (isLgn[0])
            return "naver";
        else if (isLgn[1])
            return "facebook";
        else if (isLgn[2])
            return "guest";
        else
            return "";

    }

    //로그인 버튼을 만드는 함수이다.
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

    //로그인 화면을 보여주는 함수이다.
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

    //버튼을 누를 시 실행되는 함수이다.
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

            this.checkId();

            (new Base()).saveLoginInforamtion(ID, getLoginMethod());

            ACTIVITY.checkUTD();

        }

    }

    //최근 로그인 METHOD를 얻을 때 사용된 함수이다.
    public int getLoginMethod() {

        if (isLgn[0])
            return 0;
        else if (isLgn[1])
            return 1;
        else if (isLgn[2])
            return 2;
        else return -1;

    }

    //로그인 여부를 체크하는 함수이다.
    public boolean isLogined() {

        isLgn[0] = (new TypeNaver(CONTEXT)).isLogined();
        isLgn[1] = false;
        isLgn[2] = (lsLgM == 3) && (new TypeDummy()).isLogined();

        return (isLgn[0] || isLgn[1] || isLgn[2]);

    }

}
