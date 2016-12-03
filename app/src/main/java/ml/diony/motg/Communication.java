package ml.diony.motg;

import android.app.Activity;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nayak on 2016-12-03.
 */

public class Communication extends Activity {

    protected static String TAG = "MOTGComm";

    protected static String SRVU = "http://dn-mt-svc.yuoa.ml/MOTG";

    protected String DB_VERSION = "0.0", KR_VERSION = "0.0", JP_VERSION = "0.0", WE_VERSION = "0.0", CN_VERSION = "0.0";

    public Communication() {

        //Update Versions from Local Data!!

    }

    public void versionCheck() {

        String SURL = SRVU + "VersionCheck";

        Log.i(TAG, "Version Check Starts.");

        HttpClient C = new DefaultHttpClient();

        HttpPost P = new HttpPost(SURL);

        JSONObject VX = new JSONObject();
        JSONObject INDX = new JSONObject();

        try {

            VX.put("APV", BuildConfig.VERSION_NAME);
            VX.put("INDXV", DB_VERSION);

            INDX.put("KR", KR_VERSION);
            INDX.put("JP", JP_VERSION);
            INDX.put("CN", CN_VERSION);
            INDX.put("WE", WE_VERSION);

        } catch (JSONException E) {}

        ArrayList<NameValuePair> D = new ArrayList<NameValuePair>();
        D.add(new BasicNameValuePair("VX", Base64.encodeToString(VX.toString().getBytes(), 0)));
        D.add(new BasicNameValuePair("INDX", Base64.encodeToString(INDX.toString().getBytes(), 0)));

        try {

            UrlEncodedFormEntity EN = new UrlEncodedFormEntity(D, HTTP.UTF_8);

            P.setEntity(EN);
            HttpResponse PO = C.execute(P);

            HttpEntity RE = PO.getEntity();

            if(RE != null) {

                Log.i(TAG, EntityUtils.toString(RE));

            }

        } catch (Exception E) {

            E.printStackTrace();

        }

    }

}
