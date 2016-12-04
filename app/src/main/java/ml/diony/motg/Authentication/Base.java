package ml.diony.motg.Authentication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by nayak on 2016-12-03.
 */

public class Base extends Activity {

    protected static String TAG = "MOTGAuth";

    @Override
    public void onCreate(Bundle s) {

        Log.i(TAG, "Communication of Authentication.");

        super.onCreate(s);

        this.setTitle("MOTG Login");

    }

    @Override
    public void onDestroy() {

        super.onDestroy();

    }

    public void saveLoginInformation(final String ID, final String TYPE) {

        FileOutputStream FOS;
        DataOutputStream DOS;

        try {

            File FI = new File(getCacheDir().getCanonicalPath() + "/LI.json");

            FI.createNewFile();

            FOS = new FileOutputStream(FI);
            DOS = new DataOutputStream(FOS);

            JSONObject X = new JSONObject();
            X.put("ID", ID);
            X.put("TYPE", TYPE);

            DOS.write(X.toString().getBytes("UTF-8"));

            DOS.close();
            FOS.close();

        } catch (Exception I) {
        }

    }

    public JSONObject getLoginInformation() {

        String O = null;

        JSONObject RT = null;

        try {

            InputStream IS = new FileInputStream(new File(getCacheDir().getCanonicalPath() + "/LI.json"));

            int size = IS.available();

            byte[] buffer = new byte[size];

            IS.read(buffer);

            IS.close();

            O = new String(buffer);

            RT = new JSONObject(O);

        } catch (Exception I) {}

        return RT;

    }

}
