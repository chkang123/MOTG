package ml.diony.motg.Authentication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

//Copyright 2017 YUOA.

public class Base extends Activity {

    protected static String TAG = "MOTGAuth";

    //File Stream을 String으로 바꾸는 함수이다.
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

    //파일을 읽어들이는 함수이다.
    public static String getStringFromFile(String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    @Override
    public void onCreate(Bundle s) {

        Log.i(TAG, "Communication of Authentication.");

        super.onCreate(s);

        this.setTitle("MOTG Login");

    }

    //로그인 정보를 저장하는 함수이다.
    final public void saveLoginInforamtion(String ID, int TYPE) {

        if (TYPE == 0) {

            saveLoginInformation(ID, "naver");

        } else if (TYPE == 1) {

            saveLoginInformation(ID, "facebook");

        } else if (TYPE == 2) {

            saveLoginInformation(ID, "guest");

        }

    }

    final public void saveLoginInformation(final String ID, final String TYPE) {

        Log.i(TAG + "_sLI", "ID : " + ID + ", TYPE : " + TYPE);

        FileOutputStream FOS;
        DataOutputStream DOS;

        try {

            File FI = new File("/data/data/ml.diony.motg/cache/LI.json");

            //Log.i("fifififi", getCacheDir().getCanonicalPath() + "/LI.json");

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

    //cache 폴더에서 로그인 정보를 불러오는 함수이다.
    public JSONObject getLoginInformation() {

        String O = null;

        JSONObject RT = null, DX = null;

        int i = 0;

        try {

            Log.i("Check", "STEP " + i++);

            Log.i("Check2", "It is /data/data/ml.diony.motg/cache/LI.json");

            Log.i("Check3", getStringFromFile("/data/data/ml.diony.motg/cache/LI.json"));

            DX = new JSONObject("{\"error\": \"DIED\"}");

            RT = new JSONObject(getStringFromFile("/data/data/ml.diony.motg/cache/LI.json"));

        } catch (Exception I) {

            //return DX;

        }

        return RT;

    }

}
