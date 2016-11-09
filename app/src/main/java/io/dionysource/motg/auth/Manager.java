package io.dionysource.motg.auth;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.dionysource.motg.BuildConfig;
import io.dionysource.motg.R;


/**
 * Created by nayak on 2016-11-06. —
 */

public class Manager {

    public static final float dp2px(Context context, int dp) { float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics()); return pixels * dp; }

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

        RelativeLayout I = new RelativeLayout(A);

        I.setLayoutParams(buttonParams);
        I.setId(View.generateViewId());
        I.setBackground(getDrawable(A, background));

        ImageView Logo = new ImageView(A);
        Logo.setAdjustViewBounds(true);
        Logo.setId(View.generateViewId());
        Logo.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Logo.setImageResource(symbol);

        RelativeLayout.LayoutParams bdParams = new RelativeLayout.LayoutParams(1, (int) dp2px(A, 6));
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

    public Manager() {

        //loginCheckboard에 값 넣어주기.

        loginCheckboard = new boolean[4];

        loginCheckboard[0] = naverMaster.isLogined();
        loginCheckboard[1] = facebookMaster.isLogined();
        loginCheckboard[2] = googleMaster.isLogined();
        loginCheckboard[3] = kakaoMaster.isLogined();

    }

    public boolean[] loginCheckboard; //Naver, Facebook, Google, Kakao

    public boolean isLogined() {

        return (loginCheckboard[0] || loginCheckboard[1] || loginCheckboard[2] || loginCheckboard[3]);

    }

    public void authScreenView(Activity A) {

        //Make Relative Layout

        RelativeLayout authScreen = new RelativeLayout(A);
        authScreen.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        RelativeLayout authBox = new RelativeLayout(A);
        authBox.setId(View.generateViewId());
        authBox.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        authBox.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        authBox.setBackgroundColor(getColor(A, R.color.semi_transparent_black));
        authBox.setPadding(0, 0, 0, (int) dp2px(A, 6));

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
        version.setPadding(10, 5, 10, 5);

        //Make Naver Login Button

        RelativeLayout naverButton = makeLoginButton(A, R.drawable.lb_naver, R.drawable.ic_naver, R.color.white, R.string.naver_login);
        naverButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //naverLoginActivity 실행!!

            }

        });

        //Make Facebook Login Button

        RelativeLayout facebookButton = makeLoginButton(A, R.drawable.lb_facebook, R.drawable.ic_facebook, R.color.white, R.string.facebook_login, naverButton);

        //Make Google Login Button

        RelativeLayout googleButton = makeLoginButton(A, R.drawable.lb_google, R.drawable.ic_google, R.color.google_text, R.string.google_login, facebookButton);

        //Make Kakao Login Button

        RelativeLayout kakaoButton = makeLoginButton(A, R.drawable.lb_kakao, R.drawable.ic_kakao, R.color.kakao_text, R.string.kakao_login, googleButton);

        //Add Buttons and Display

        authBox.addView(naverButton);
        authBox.addView(facebookButton);
        authBox.addView(googleButton);
        authBox.addView(kakaoButton);

        logoBox.addView(logo);

        authScreen.addView(background);
        authScreen.addView(version);
        authScreen.addView(authBox);
        authScreen.addView(logoBox);

        A.setContentView(authScreen);

    }

}