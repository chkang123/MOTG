package ml.diony.motg.Communication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ml.diony.motg.Authentication.Base;
import ml.diony.motg.R;

import static android.content.ContentValues.TAG;

//Copyright 2017 YUOA.

public class Interaction extends Activity {

    final static private String URL = "http://dn-mt-svc.yuoa.ml/MOTGDataAction";
    private Context CONTEXT;
    private JSONArray O;

    private boolean BOOO = false;

    public Interaction(Activity A, Context C) {

        CONTEXT = C;

    }

    //Interaction은 Activity로서의 기능이 로딩 바를 보여주는 것 밖에 없다.
    @Override
    public void onCreate(Bundle s) {

        super.onCreate(s);

        setContentView(R.layout.loading);

        final RelativeLayout RL = (RelativeLayout) findViewById(R.id.ld);

        Animation AN = AnimationUtils.loadAnimation(this, R.anim.fade_in_fast);

        RL.startAnimation(AN);

    }

    //뒤로가기 버튼을 눌러 이 액티비티에 resume하면 액티비티를 종료시킨다.
    @Override
    public void onResume() {

        super.onResume();

        if (BOOO) {

            finish();

        } else {

            BOOO = true;

        }

    }

    //음식점 TYPE(ALL, KR, CN, JP, WE)을 지정하여 해당 타입의 모든 음식점 정보를 받아온 이후에 Intent에 받아온 정보를 주어 activity를 시작한다.
    public JSONArray getAll(final String TYPE, final Intent I) {

        Intent IN = new Intent(CONTEXT, Interaction.class);

        CONTEXT.startActivity(IN);

        O = new JSONArray();

        new Thread() {

            public void run() {

                HttpClient C = new DefaultHttpClient();

                HttpPost P = new HttpPost(URL + "Recommended");

                P.addHeader("X-Dionysource", "MOTG_AP");
                P.setHeader("Content-Type", "application/json");

                ArrayList<NameValuePair> D = new ArrayList<NameValuePair>();
                D.add(new BasicNameValuePair("RQT", TYPE));

                InputStream IS = null;
                String RS = null;

                try {

                    UrlEncodedFormEntity EN = new UrlEncodedFormEntity(D, HTTP.UTF_8);

                    P.setEntity(EN);
                    HttpResponse PO = C.execute(P);

                    HttpEntity RE = PO.getEntity();

                    if (RE != null) {

                        //Log.i(TAG, EntityUtils.toString(RE));

                        //DATA Received.

                        IS = RE.getContent();
                        BufferedReader RD = new BufferedReader(new InputStreamReader(IS, "UTF-8"));
                        StringBuilder SB = new StringBuilder();

                        String LN = null;

                        while ((LN = RD.readLine()) != null) {

                            SB.append(LN + "\n");

                        }

                        RS = SB.toString();

                        Log.i(TAG, RS);

                        setO(new JSONArray(RS));

                        //JSON parsing Completed!

                        I.putExtra("ALLX", O.toString());
                        CONTEXT.startActivity(I);

                    }

                } catch (Exception E) {

                    E.printStackTrace();

                } finally {

                }

            }

        }.start();

        Log.i("XXX", O.toString());

        return O;

    }

    //특정한 KIND(NAME이나 REGION 지정)의 음식점(들)을 받아온 이후에 Intent에 받아온 정보를 주어 activity를 시작한다.
    public JSONArray getSpecified(final String TYPE, final String STL, final String STLD, final Intent I) {

        O = new JSONArray();

        Intent IN = new Intent(CONTEXT, Interaction.class);

        CONTEXT.startActivity(IN);

        new Thread() {

            public void run() {

                HttpClient C = new DefaultHttpClient();

                HttpPost P = new HttpPost(URL);

                P.addHeader("X-Dionysource", "MOTG_AP");
                P.setHeader("Content-Type", "application/json");

                ArrayList<NameValuePair> D = new ArrayList<NameValuePair>();
                D.add(new BasicNameValuePair("RQT", TYPE));
                D.add(new BasicNameValuePair("SLT", STL));
                D.add(new BasicNameValuePair("SLTD", STLD));

                InputStream IS = null;
                String RS = null;

                try {

                    UrlEncodedFormEntity EN = new UrlEncodedFormEntity(D, HTTP.UTF_8);

                    P.setEntity(EN);
                    HttpResponse PO = C.execute(P);

                    HttpEntity RE = PO.getEntity();

                    if (RE != null) {

                        //Log.i(TAG, EntityUtils.toString(RE));

                        //DATA Received.

                        IS = RE.getContent();
                        BufferedReader RD = new BufferedReader(new InputStreamReader(IS, "UTF-8"));
                        StringBuilder SB = new StringBuilder();

                        String LN = null;

                        while ((LN = RD.readLine()) != null) {

                            SB.append(LN + "\n");

                        }

                        RS = SB.toString();

                        Log.i(TAG, RS);

                        setO(new JSONArray(RS));

                        //JSON parsing Completed!

                        I.putExtra("ALLX", O.toString());
                        CONTEXT.startActivity(I);

                    }

                } catch (Exception E) {

                    E.printStackTrace();

                } finally {

                }

            }

        }.start();

        Log.i("XXX", O.toString());

        return O;

    }

    //추천 메뉴를 받아온 이후 추천 메뉴 리스트 intent를 시작한다.
    public JSONArray getRecommended(final Intent I) {

        Intent IN = new Intent(CONTEXT, Interaction.class);

        CONTEXT.startActivity(IN);

        O = new JSONArray();

        new Thread() {

            public void run() {

                final HttpClient C = new DefaultHttpClient();

                final HttpPost P = new HttpPost("http://dn-mt-svc.yuoa.ml/MOTGRecommended");

                P.addHeader("X-Dionysource", "MOTG_AP");
                P.setHeader("Content-Type", "application/json");

                //final JSONObject AX = new JSONObject();

                final ArrayList<NameValuePair> D = new ArrayList<NameValuePair>();

                Handler mHandler = new Handler(Looper.getMainLooper());

                mHandler.postDelayed(new Runnable() {

                    @Override

                    public void run() {


                        //final JSONObject AX = ;
                        D.add(new BasicNameValuePair("AX", Base64.encodeToString((new Base()).getLoginInformation().toString().getBytes(), 0)));


                        (new Thread() {

                            @Override
                            public void run() {

                                try {

                                    UrlEncodedFormEntity EN = new UrlEncodedFormEntity(D, HTTP.UTF_8);

                                    P.setEntity(EN);
                                    Log.i("EMM??", "EMM?????");
                                    HttpResponse PO = C.execute(P);

                                    Log.i("EMM??", "EMM?????");
                                    HttpEntity RE = PO.getEntity();

                                    if (RE != null) {

                                        //Log.i(TAG, EntityUtils.toString(RE));

                                        InputStream IS = null;
                                        String RS = null;

                                        //DATA Received.

                                        IS = RE.getContent();
                                        BufferedReader RD = new BufferedReader(new InputStreamReader(IS, "UTF-8"));
                                        StringBuilder SB = new StringBuilder();

                                        String LN = null;

                                        while ((LN = RD.readLine()) != null) {

                                            SB.append(LN + "\n");

                                        }

                                        RS = SB.toString();

                                        Log.i(TAG, RS);

                                        setO(new JSONArray(RS));

                                        //JSON parsing Completed!

                                        Log.i("START", "STATRTTTT");
                                        I.putExtra("ALLX", O.toString());
                                        CONTEXT.startActivity(I);

                                    }

                                } catch (Exception E) {

                                    E.printStackTrace();

                                } finally {

                                }

                            }

                        }).start();

                        Log.i("HANDLEREND??", "ENDED!!");

                    }

                }, 0);

                Log.i("HANDLEREND??", "ENDED!!");


            }

        }.start();

        Log.i("XXX", O.toString());

        return O;

    }

    //Thread 안에서 현재 Activity의 JSONArray에 값을 넣기 위한 함수이다.
    private void setO(JSONArray O) {
        this.O = O;
    }

}
