package ml.diony.motg.Communication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import ml.diony.motg.R;

import static android.content.ContentValues.TAG;

/**
 * Created by nayak on 2016-12-05.
 */

public class Interaction extends Activity {

    private Activity ACTIVITY;
    private Context CONTEXT;
    final static private String URL = "http://dn-mt-svc.yuoa.ml/MOTGDataAction";

    private JSONArray O;

    private boolean BOOO = false;

    @Override
    public void onCreate(Bundle s) {

        super.onCreate(s);

        setContentView(R.layout.loading);

        final RelativeLayout RL = (RelativeLayout) findViewById(R.id.ld);

        Animation AN = AnimationUtils.loadAnimation(this, R.anim.fade_in_fast);

        RL.startAnimation(AN);

    }

    @Override
    public void onResume() {

        super.onResume();

        if(BOOO) {

            finish();

        } else {

            BOOO = true;

        }

    }

    public Interaction(Activity A, Context C) {

        ACTIVITY = A;
        CONTEXT= C;

    }

    public Interaction() {

        ACTIVITY = null;
        CONTEXT = null;

    }

    private void setO(JSONArray O) { this.O = O; }

    public JSONArray getAll(Intent I) {

        return getAll("ALL", I);

    }

    public JSONArray getAll(final String TYPE, final Intent I) {

        Intent IN = new Intent(CONTEXT, Interaction.class);

        CONTEXT.startActivity(IN);

        O = new JSONArray();

        new Thread() {

            public void run() {

                HttpClient C = new DefaultHttpClient();

                HttpPost P = new HttpPost(URL);

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

    public JSONArray getO() {return O;}

}
