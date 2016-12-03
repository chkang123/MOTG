package io.dionysource.motg.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.dionysource.motg.BuildConfig;
import io.dionysource.motg.R;


/**
 * Created by nayak on 2016-11-06. —
 */

public class Manager extends Activity implements OnClickListener {

    protected String TAG = "MOTGAuth_man";

    private Context CXT;

    private byte lastLoginMethod = 0;

    private int[] loginButtonId = { 0, 0, 0, 0 }; //Naver, Facebook, Dummy, ???

    public boolean[] loginCheckboard = { false, false, false, false }; //Naver, Facebook, Google, Kakao

    public Manager(Context CXT) {

        Log.i(TAG, "Auth Manager Constructed.");

        //loginCheckboard에 값 넣어주기.

        loginCheckboard = new boolean[4];

        loginCheckboard[0] = (new naverMaster(CXT)).isLogined();
        loginCheckboard[1] = facebookMaster.isLogined();
        loginCheckboard[2] = (new dummyMaster()).isLogined();

    }

    public Manager() {}

    public static final float dp2px(Context context, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10 * dp, context.getResources().getDisplayMetrics());
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

        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams((int) dp2px(A, 40), (int) dp2px(A,6));//ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(40, 5, 40, 5);

        return makeLoginButton(A, background, symbol, color, message, buttonParams);

    }
    private RelativeLayout makeLoginButton(Activity A, int background, int symbol, int color, int message, RelativeLayout below) {

        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams((int) dp2px(A, 40), (int) dp2px(A,6));//ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(40, 20, 40, 20);
        buttonParams.addRule(RelativeLayout.ABOVE, below.getId());

        return makeLoginButton(A, background, symbol, color, message, buttonParams);

    }
    private RelativeLayout makeLoginButton(Activity A, int background, int color, int message, RelativeLayout below) {

        Log.i(TAG, "A Login Button is created.");

        RelativeLayout I = new RelativeLayout(A);

        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams((int) dp2px(A, 40), (int) dp2px(A,6));//ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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

    public boolean isLogined() {

        return (loginCheckboard[0] || loginCheckboard[1] || loginCheckboard[2] || loginCheckboard[3]);

    }

    public void authScreenView(Activity A, Context C) {

        Log.i(TAG, "Auth Screen Started.");

        CXT = C;

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
        logo.setImageResource(R.drawable.id_motg_full);

        //Make Background Image

        ImageView background = new ImageView(A);
        background.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        background.setImageResource(R.drawable.splash_back);
        background.setScaleType(ImageView.ScaleType.CENTER_CROP);

        //Make Version Information

        TextView version = new TextView(A);
        version.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        version.setText(BuildConfig.VERSION_NAME);
        version.setTextColor(getColor(A, R.color.white));
        version.setTextSize(13);
        version.setPadding(10, 10 + ((android.os.Build.VERSION.SDK_INT > 20) ? (int) dp2px(A, 2) : 0), 10, 10);

        //Make Naver Login Button

        RelativeLayout naverButton = makeLoginButton(A, R.drawable.lb_naver, R.drawable.ic_naver, R.color.white, R.string.naver_login);
        naverButton.setId(View.generateViewId());
        loginButtonId[0] = naverButton.getId();
        naverButton.setOnClickListener(this);

        //Make Facebook Login Button

        RelativeLayout facebookButton = makeLoginButton(A, R.drawable.lb_facebook, R.drawable.ic_facebook, R.color.white, R.string.facebook_login, naverButton);
        facebookButton.setId(View.generateViewId());
        loginButtonId[1] = facebookButton.getId();
        facebookButton.setOnClickListener(this);

        //Make Dummy Login Button
        RelativeLayout dummyButton = makeLoginButton(A, R.drawable.lb_dummy, R.color.dummy_text, R.string.dummy_login, facebookButton);
        dummyButton.setId(View.generateViewId());
        loginButtonId[2] = dummyButton.getId();
        dummyButton.setOnClickListener(this);

        //Add Buttons and Display

        authBox.addView(naverButton);
        authBox.addView(facebookButton);
        authBox.addView(dummyButton);

        logoBox.addView(logo);

        authScreen.addView(background);
        authScreen.addView(version);
        authScreen.addView(authBox);
        authScreen.addView(logoBox);

        A.setContentView(authScreen);

    }

    @Override
    public void onClick(View v) {

        Log.i(TAG, "Click detected.");

        if(v.getId() == loginButtonId[0]) {

            Log.i(TAG, "Naver Button Clicked.");

            lastLoginMethod = 1;

            Intent loginIntent = new Intent(CXT, naverLogin.class);
            CXT.startActivity(loginIntent);

        } else if(v.getId() == loginButtonId[1]) {

            Log.i(TAG, "Facebook Button Clicked.");

            lastLoginMethod = 2;

            //Intent loginIntent = new Intent(A, naverLogin.class);
            //startActivity(loginIntent);

        } else if(v.getId() == loginButtonId[2]) {

            Log.i(TAG, "Dummy Login Button Clicked.");

            lastLoginMethod = 3;

            //Intent loginIntent = new Intent(A, kakaoLogin.class);
            //startActivity(loginIntent);

        }

    }

}