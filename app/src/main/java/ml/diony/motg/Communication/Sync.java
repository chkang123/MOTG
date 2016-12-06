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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    protected Intent LSTI;

    protected init INIT = null;

    final protected File FP;

    public Sync(File FP, Context CONTEXT, Activity ACTIVITY, init INIT) {

        this.FP = FP;

        this.CONTEXT = CONTEXT;
        this.ACTIVITY = ACTIVITY;
        this.INIT = INIT;

        //Update Versions from Local Data!!

    }

    public Sync(Context CONTEXT, Activity ACTIVITY) {

        this.CONTEXT = CONTEXT;
        this.ACTIVITY = ACTIVITY;

        FP = null;

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

    private boolean isVC = true;

    @Override
    public void onCreate(Bundle s) {

        super.onCreate(s);

        this.setContentView(R.layout.loading);

        final RelativeLayout RL = (RelativeLayout) findViewById(R.id.ld);

        Animation AN = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        //isVC = getIntent().getExtras().getBoolean("isVX");

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

                    AX.put("ID", INIT.S.checkId().getId());
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

                            final JSONObject CNK = new JSONObject(new String(Base64.decode(RX.getString("chunk"), 0)));

                            Log.i(TAG, CNK.toString());

                            if (isInit) {

                                downloadUS();

                                Handler MH = new Handler(Looper.getMainLooper());

                                MH.postDelayed(new Runnable() {

                                    @Override

                                    public void run() {

                                        //GO TO REAL MAIN!!!!

                                        Log.i(TAG, "Go to main screen");

                                        Toast.makeText(CONTEXT, "Welcome to MOTG", Toast.LENGTH_LONG).show();


                                        try {

                                            historySync(true, String.valueOf(CNK.getInt("HISTORY_REV")));

                                        } catch (Exception E) {
                                        }

                                    }

                                }, 0);


                            } else {

                                historySync(String.valueOf(CNK.getInt("HISTORY_REV")));

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

    public void historySync(String REV) {

        historySync(false, REV);

    }

    public void historySync() {

        accountSync(false);

    }

    public void historySync(final boolean I, final String REV) {

        final String SURL = SRVU + "HistorySync";

        final int CLIE_REV = getREVINFO(), SERV_REV = Integer.parseInt(REV);

        Log.i(TAG, "Synchronizing History Starts.");

        isVC = false;

        if (!I) {

            Intent IN = new Intent(CONTEXT, Interaction.class);

            LSTI = IN;
            //IN.putExtra("isVX", (Boolean) false);
            //CONTEXT.startActivity(IN);

        }

        new Thread() {

            public void run() {

                HttpClient C = new DefaultHttpClient();

                HttpPost P = new HttpPost(SURL);

                P.addHeader("X-Dionysource", "MOTG_AP");
                P.setHeader("Content-Type", "application/json");

                JSONObject AX = new JSONObject();
                JSONObject REX = new JSONObject();

                try {

                    AX.put("ID", INIT.S.checkId().getId());
                    AX.put("TYPE", INIT.S.getType());

                    if (SERV_REV > CLIE_REV) {

                        //Server is latest;

                        Log.i(TAG, "SHOULD DOWNLOAD!!");

                        REX.put("COMMAND", "DOWNLOAD");

                    } else if (SERV_REV < CLIE_REV) {

                        //Client is more latest!!!!! SIVAL!!!! WOW!!!HOLLYPOP!!!

                        Log.i(TAG, "SHOULD UPLOAD!!");

                        REX.put("COMMAND", "UPLOAD");

                        REX.put("REV", CLIE_REV);

                        JSONArray JJX = new JSONArray();

                        for (int i = 0; i < CLIE_REV; i++) {

                            int x = i + 1;

                            JJX.put(getHISTORY(x));

                            Log.i("SYNCSYNCHISTORYUP_" + x, getHISTORY(x).toString());

                        }

                        REX.put("ARRAY", JJX);

                    } else {

                        //Same Revision

                        Log.i(TAG, "SHOLUD NOT DO ANYTHING!!!!GG");

                        if (I) {

                            Log.i(TAG, "SO, STARTS ACTIVITY!!!");

                            Intent I = new Intent(ACTIVITY, ml.diony.motg.Display.MainActivity.class);

                            ACTIVITY.startActivity(I);

                        } else {

                        }

                        return;

                    }

                } catch (JSONException E) {
                }

                ArrayList<NameValuePair> D = new ArrayList<NameValuePair>();
                D.add(new BasicNameValuePair("AX", Base64.encodeToString(AX.toString().getBytes(), 0)));
                D.add(new BasicNameValuePair("REX", Base64.encodeToString(REX.toString().getBytes(), 0)));

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

                        Log.i(TAG + "_history_verify", RS);

                        JSONObject RX = new JSONObject(RS);

                        //JSON parsing Completed!

                        if (RX.isNull("error")) {

                            //No error

                            try {

                                if (SERV_REV > CLIE_REV) {

                                    //DOWNLOAD????

                                    JSONArray RRX = RX.getJSONArray("ARRAY");

                                    for (int i = 1; i <= RRX.length(); i++) {

                                        saveHISTORY(i, (JSONObject) RRX.get(i - 1));
                                        Log.i(TAG, "SAVE(REV=" + i + "): " + ((JSONObject) RRX.get(i - 1)).toString());

                                    }

                                    saveREVINFO(RX.getInt("REV"));

                                }

                            } finally {

                                if (I) {

                                    Intent IX = new Intent(CONTEXT, ml.diony.motg.Display.MainActivity.class);

                                    startActivity(IX);

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

    public void downloadUS() {

        final String SURL = SRVU + "USSync";

        Log.i(TAG, "Synchronizing UserSetting Starts.");

        new Thread() {

            public void run() {

                HttpClient C = new DefaultHttpClient();

                HttpPost P = new HttpPost(SURL);

                P.addHeader("X-Dionysource", "MOTG_AP");
                P.setHeader("Content-Type", "application/json");

                JSONObject AX = new JSONObject();
                JSONObject REX = new JSONObject();

                try {

                    AX.put("ID", INIT.S.checkId().getId());
                    AX.put("TYPE", INIT.S.getType());

                    //Server is latest;

                    Log.i(TAG, "SHOULD DOWNLOAD!!");

                    REX.put("COMMAND", "DOWNLOAD");


                } catch (JSONException E) {
                }

                ArrayList<NameValuePair> D = new ArrayList<NameValuePair>();
                D.add(new BasicNameValuePair("AX", Base64.encodeToString(AX.toString().getBytes(), 0)));
                D.add(new BasicNameValuePair("REX", Base64.encodeToString(REX.toString().getBytes(), 0)));

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

                        Log.i(TAG + "_history_verify", RS);

                        JSONArray RX = new JSONArray(RS);

                        //JSON parsing Completed!

                            //No error

                        saveU_S(RX);

                    }

                } catch (Exception E) {

                    E.printStackTrace();

                }

            }

        }.start();

    }

    public void uploadUS(final JSONArray JA) {

        final String SURL = SRVU + "USSync";

        Log.i(TAG, "Synchronizing UserSetting Starts.");

        new Thread() {

            public void run() {

                HttpClient C = new DefaultHttpClient();

                HttpPost P = new HttpPost(SURL);

                P.addHeader("X-Dionysource", "MOTG_AP");
                P.setHeader("Content-Type", "application/json");

                JSONObject AX = new JSONObject();
                JSONObject REX = new JSONObject();

                try {

                    AX.put("ID", INIT.S.checkId().getId());
                    AX.put("TYPE", INIT.S.getType());

                    //Server is latest;

                    Log.i(TAG, "SHOULD UPLOAD!!");

                    REX.put("COMMAND", "UPLOAD");
                    REX.put("ARRAY", JA);


                } catch (JSONException E) {
                }

                ArrayList<NameValuePair> D = new ArrayList<NameValuePair>();
                D.add(new BasicNameValuePair("AX", Base64.encodeToString(AX.toString().getBytes(), 0)));
                D.add(new BasicNameValuePair("REX", Base64.encodeToString(REX.toString().getBytes(), 0)));

                InputStream IS = null;
                String RS = null;

                try {

                    UrlEncodedFormEntity EN = new UrlEncodedFormEntity(D, HTTP.UTF_8);

                    P.setEntity(EN);
                    HttpResponse PO = C.execute(P);

                    HttpEntity RE = PO.getEntity();

                    if (RE != null) {

                        Log.i(TAG, RE.toString());

                    }

                } catch (Exception E) {

                    E.printStackTrace();

                }

            }

        }.start();

    }

    final public void saveREVINFO(final int REV) {

        FileOutputStream FOS;
        DataOutputStream DOS;

        try {

            File FI = new File("/data/data/ml.diony.motg/cache/HR.json");

            //Log.i("fifififi", getCacheDir().getCanonicalPath() + "/LI.json");

            FI.createNewFile();

            FOS = new FileOutputStream(FI);
            DOS = new DataOutputStream(FOS);

            JSONObject X = new JSONObject();
            X.put("REV", REV);

            DOS.write(X.toString().getBytes("UTF-8"));

            DOS.close();
            FOS.close();

        } catch (Exception I) {
        }

    }

    final public void saveHISTORY(final int REV, final JSONObject JSON) {

        FileOutputStream FOS;
        DataOutputStream DOS;

        try {

            File FI = new File("/data/data/ml.diony.motg/cache/HS_" + REV + ".json");

            //Log.i("fifififi", getCacheDir().getCanonicalPath() + "/LI.json");

            FI.createNewFile();

            FOS = new FileOutputStream(FI);
            DOS = new DataOutputStream(FOS);

            DOS.write(JSON.toString().getBytes("UTF-8"));

            Log.i("SaveHISTORY", JSON.toString());

            DOS.close();
            FOS.close();

        } catch (Exception I) {
        }

    }

    final public void saveU_S(final JSONArray JA) {

        FileOutputStream FOS;
        DataOutputStream DOS;

        try {

            File FI = new File("/data/data/ml.diony.motg/cache/USet.json");

            //Log.i("fifififi", getCacheDir().getCanonicalPath() + "/LI.json");

            FI.createNewFile();

            FOS = new FileOutputStream(FI);
            DOS = new DataOutputStream(FOS);

            DOS.write(JA.toString().getBytes("UTF-8"));

            Log.i("SaveHISTORY", JA.toString());

            DOS.close();
            FOS.close();

        } catch (Exception I) {
        }

        if (JA != null)
            Log.i("SETUS", JA.toString());
        else
            Log.i("SETUS", "NULL");

    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile(String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    public int getREVINFO() {

        String O = null;

        JSONObject RT = null;

        int REV = 0;

        int i = 0;

        try {

            Log.i("Check", "STEP " + i++);

            Log.i("Check2", "It is /data/data/ml.diony.motg/cache/HR.json");

            Log.i("Check3", getStringFromFile("/data/data/ml.diony.motg/cache/HR.json"));

            RT = new JSONObject(getStringFromFile("/data/data/ml.diony.motg/cache/HR.json"));

            REV = RT.getInt("REV");

        } catch (Exception I) {

            //return DX;

        }

        return REV;

    }

    public JSONObject getHISTORY(final int REV) {

        JSONObject RT = null;

        try {

            RT = new JSONObject(getStringFromFile("/data/data/ml.diony.motg/cache/HS_" + REV + ".json"));

        } catch (Exception I) {

            //return DX;

        }

        return RT;

    }

    public JSONArray getU_S() {

        JSONArray RT = null;

        try {

            RT = new JSONArray(getStringFromFile("/data/data/ml.diony.motg/cache/USet.json"));

        } catch (Exception I) {

            //return DX;

        }

        if (RT != null)
            Log.i("GETUS", RT.toString());
        else
            Log.i("GETUS", "NULL");

        return RT;

    }

}
