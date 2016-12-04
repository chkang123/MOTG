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
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ml.diony.motg.R;
import ml.diony.motg.init;

/**
 * Created by nayak on 2016-12-03.
 */

public class Sync extends Activity {

    protected static String TAG = "MOTGComm";

    protected static String SRVU = "http://dn-mt-svc.yuoa.ml/MOTG";

    protected String DB_VERSION = "0.0", KR_VERSION = "0.0", JP_VERSION = "0.0", WE_VERSION = "0.0", CN_VERSION = "0.0";

    protected Context CONTEXT = this;

    protected boolean cCK = false;

    protected Activity ACTIVITY = this;

    protected init INIT = null;

    final protected File FP;

    public Sync(File FP, Context CONTEXT, Activity ACTIVITY, init INIT) {

        this.FP = FP;

        this.CONTEXT = CONTEXT;
        this.ACTIVITY = ACTIVITY;
        this.INIT = INIT;

        //Update Versions from Local Data!!

    }

    public Sync() {

        this.FP = null;

    }

    private boolean isFile(File file) {
        boolean result;
        if (file != null && file.exists() && file.isFile()) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public void onCreate(Bundle s) {

        super.onCreate(s);

        this.setContentView(R.layout.loading);

        final RelativeLayout RL = (RelativeLayout) findViewById(R.id.ld);

        Animation AN = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        RL.startAnimation(AN);

        versionCheck();

    }

    @Override
    public void onResume() {

        super.onResume();

        if (cCK) {

            this.finishAffinity();

        } else {

            cCK = true;

        }

    }

    public void versionCheck() {

        final String SURL = SRVU + "VersionCheck";

        Log.i(TAG, "Version Check Starts.");

        final TextView TXV = (TextView) ACTIVITY.findViewById(R.id.ldT);

        //TXV.setText("Checking version...");

        new Thread() {

            public void run() {

                HttpClient C = new DefaultHttpClient();

                HttpPost P = new HttpPost(SURL);

                P.addHeader("X-Dionysource", "MOTG_AP");
                P.setHeader("Content-Type", "application/json");

                JSONObject VX = new JSONObject();
                JSONObject INDX = new JSONObject();

                try {

                    VX.put("APV", ml.diony.motg.BuildConfig.VERSION_NAME);
                    VX.put("INDXV", DB_VERSION);

                    INDX.put("KR", KR_VERSION);
                    INDX.put("JP", JP_VERSION);
                    INDX.put("CN", CN_VERSION);
                    INDX.put("WE", WE_VERSION);

                } catch (JSONException E) {
                }

                ArrayList<NameValuePair> D = new ArrayList<NameValuePair>();
                D.add(new BasicNameValuePair("VX", Base64.encodeToString(VX.toString().getBytes(), 0)));
                D.add(new BasicNameValuePair("INDX", Base64.encodeToString(INDX.toString().getBytes(), 0)));

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

                        JSONObject RX = new JSONObject(RS);

                        //JSON parsing Completed!

                        if (RX.isNull("error")) {

                            //No error

                            /*if (!RX.isNull("command")) {

                                //There is some commands from server.

                                Log.i(TAG, "There is some commands!");

                                //{"command":[["DO_DB_UPDATE","DB.161203.0"],["DO_DB_UPDATE","KR.161202.0"],["DO_DB_UPDATE","CN.161201.0"],["DO_DB_UPDATE","JP.161204.1"],["DO_DB_UPDATE","WE.161201.0"]]}
                                //{"command":[["DO_APP_UPDATE","1.1.0"]]}

                                JSONArray RA = RX.getJSONArray("command");

                                Log.i(TAG, RA.toString());

                                for (int i = 0; i < RA.length(); i++) {

                                    JSONArray RTEMP = (JSONArray) RA.get(i);

                                    if (RTEMP.get(0).toString().equals("DO_APP_UPDATE")) {

                                        //EMPTY CODE

                                    } else if (RTEMP.get(0).toString().equals("DO_DB_UPDATE")) {

                                        String DLEMA = "http://dn-mt-fs.yuoa.ml/" + (String) RTEMP.get(1).toString() + ".db";

                                        Log.i(TAG, "Download START: " + DLEMA);

                                        SyncHttpClient HCL = new SyncHttpClient();

                                        final String PATH = RTEMP.get(2).toString();

                                        String[] ACT = new String[]{"application/x-sqlite3", "application/octet-stream"};

                                        HCL.get(DLEMA, new BinaryHttpResponseHandler(ACT) {
                                            @Override
                                            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {

                                                Log.i(TAG, "SUCCESS!!!");

                                                FileOutputStream FOS;
                                                DataOutputStream DOS;

                                                try {

                                                    File FI = new File(getFilesDir().getCanonicalPath() + "/" + PATH + ".db");

                                                    Log.i(TAG + "_" + String.valueOf(i) + ": ", FI.toString());

                                                    FI.createNewFile();

                                                    FOS = new FileOutputStream(FI);
                                                    DOS = new DataOutputStream(FOS);

                                                    DOS.write(bytes);

                                                    DOS.close();
                                                    FOS.close();

                                                    Log.i(TAG, String.valueOf(isFile(FI)));

                                                } catch (IOException I) {
                                                }

                                            }

                                            @Override
                                            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {

                                                Log.i(TAG, "FAIL... TT");

                                            }
                                        });

                                    }

                                }

                            }*/

                        } else {

                            //Error detected.

                            Log.i(TAG, "ERROR DETECTED.");

                            final String ERS = RX.getString("error");

                            Log.i(TAG, "ERROR: " + ERS);

                        }

                    }

                } catch (Exception E) {

                    E.printStackTrace();

                } finally {

                    File DBI = new File(CONTEXT.getFilesDir() + "/DB.db");

                    if (DBI.exists()) {

                        Log.i(TAG, "File Exists!");

                    } else {

                        Log.i(TAG, "File is not exists!");

                    }

                    accountSync(true);

                }

            }

        }.start();


    }

    public void accountSync(final boolean isInit) {

        final String SURL = SRVU + "AccountCheck";

        Log.i(TAG, "Account Check Starts.");

        new Thread() {

            public void run() {

                HttpClient C = new DefaultHttpClient();

                HttpPost P = new HttpPost(SURL);

                P.addHeader("X-Dionysource", "MOTG_AP");
                P.setHeader("Content-Type", "application/json");

                JSONObject AX = new JSONObject();

                try {

                    AX.put("ID", INIT.S.getId());
                    AX.put("TYPE", INIT.S.getType());

                } catch (JSONException E) {
                }

                ArrayList<NameValuePair> D = new ArrayList<NameValuePair>();
                D.add(new BasicNameValuePair("AX", Base64.encodeToString(AX.toString().getBytes(), 0)));
                D.add(new BasicNameValuePair("APV", ml.diony.motg.BuildConfig.VERSION_NAME));

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

                        JSONObject RX = new JSONObject(RS);

                        //JSON parsing Completed!

                        if (RX.isNull("error")) {

                            //No error

                            Log.i(TAG, "There is some chunks!");

                            Log.i(TAG, RX.getString("chunk"));

                            Log.i(TAG, new String(Base64.decode(RX.getString("chunk"), 0)));

                            JSONObject CNK = new JSONObject(new String(Base64.decode(RX.getString("chunk"), 0)));

                            Log.i(TAG, CNK.toString());

                            if (CNK.getInt("HISTORY_REV") != 1) {

                                //Sync is needed.

                                dataSync(isInit);

                            } else {

                                if (isInit) {

                                    Handler MH = new Handler(Looper.getMainLooper());

                                    MH.postDelayed(new Runnable() {

                                        @Override

                                        public void run() {

                                            //GO TO REAL MAIN!!!!

                                            Log.i(TAG, "Go to main screen");

                                            Toast.makeText(CONTEXT, "Welcome to MOTG", Toast.LENGTH_LONG).show();

                                            Intent I = new Intent(CONTEXT, ml.diony.motg.Display.MainActivity.class);

                                            CONTEXT.startActivity(I);

                                        }

                                    }, 0);

                                }

                            }

                        } else {

                            //Error detected.

                            Log.i(TAG, "ERROR DETECTED.");

                            final String ERS = RX.getString("error");

                            Log.i(TAG, "ERROR: " + ERS);

                        }

                    }

                } catch (Exception E) {

                    E.printStackTrace();

                }

            }

        }.start();

    }

    public void dataSync(final boolean isInit) {

        final String SURL = SRVU + "AccountCheck";

        Log.i(TAG, "Account Check Starts.");

        new Thread() {

            public void run() {

                HttpClient C = new DefaultHttpClient();

                HttpPost P = new HttpPost(SURL);

                P.addHeader("X-Dionysource", "MOTG_AP");
                P.setHeader("Content-Type", "application/json");

                JSONObject AX = new JSONObject();

                try {

                    AX.put("ID", "somestuffs");
                    AX.put("TYPE", "somestuffs");

                } catch (JSONException E) {
                }

                ArrayList<NameValuePair> D = new ArrayList<NameValuePair>();
                D.add(new BasicNameValuePair("AX", Base64.encodeToString(AX.toString().getBytes(), 0)));
                D.add(new BasicNameValuePair("APV", ml.diony.motg.BuildConfig.VERSION_NAME));

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

                        JSONObject RX = new JSONObject(RS);

                        //JSON parsing Completed!

                        if (RX.isNull("error")) {

                            //No error

                            Log.i(TAG, "There is some chunks!");

                            Log.i(TAG, RX.getString("chunk"));

                            Log.i(TAG, new String(Base64.decode(RX.getString("chunk"), 0)));

                            JSONObject CNK = new JSONObject(new String(Base64.decode(RX.getString("chunk"), 0)));

                            Log.i(TAG, CNK.toString());

                            if (CNK.getInt("HISTORY_REV") != 1) {

                                //Sync is needed.

                                dataSync(isInit);

                            } else {

                                if (isInit) {

                                    Handler MH = new Handler(Looper.getMainLooper());

                                    MH.postDelayed(new Runnable() {

                                        @Override

                                        public void run() {

                                            //GO TO REAL MAIN!!!!

                                            Log.i(TAG, "Go to main screen");

                                            Toast.makeText(CONTEXT, "Welcome to MOTG", Toast.LENGTH_LONG).show();

                                            Intent I = new Intent(CONTEXT, ml.diony.motg.Display.MainActivity.class);

                                            CONTEXT.startActivity(I);

                                        }

                                    }, 0);

                                }

                            }

                        } else {

                            //Error detected.

                            Log.i(TAG, "ERROR DETECTED.");

                            final String ERS = RX.getString("error");

                            Log.i(TAG, "ERROR: " + ERS);

                        }

                    }

                } catch (Exception E) {

                    E.printStackTrace();

                }

            }

        }.start();

        if (isInit) {

            Handler MH = new Handler(Looper.getMainLooper());

            MH.postDelayed(new Runnable() {

                @Override

                public void run() {

                    //GO TO REAL MAIN!!!!

                    Toast.makeText(CONTEXT, "Welcome to MOTG", Toast.LENGTH_LONG).show();

                    Intent I = new Intent(CONTEXT, ml.diony.motg.Display.MainActivity.class);

                    CONTEXT.startActivity(I);

                }

            }, 0);

        }

    }

}
