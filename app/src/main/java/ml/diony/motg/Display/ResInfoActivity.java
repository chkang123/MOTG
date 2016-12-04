package ml.diony.motg.Display;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ml.diony.motg.R;

public class ResInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_info);

        Intent intent = getIntent();
        String rname = intent.getExtras().getString("name");
        String ALLX = intent.getExtras().getString("ALLX");
        float ACOORDX = 0, ACOORDY = 0;

        Log.i("RLA", ALLX);

        JSONArray X = new JSONArray(), XM = new JSONArray();
        String XT = "", XTI = "";
        try {
            X = new JSONArray(ALLX);
            XM = ((JSONObject) X.get(0)).getJSONArray("MENU");
            String XXX = ((JSONObject)X.get(0)).getJSONArray("POS").toString(), XRE = XXX.substring(XXX.indexOf(",") + 1, XXX.indexOf("]"));
            Log.i("WOWWOWOWWO", XRE);
            ACOORDX = Float.parseFloat(XRE.substring(0, XRE.indexOf(",")));
            ACOORDY = Float.parseFloat(XRE.substring(XRE.indexOf(",") + 1));
            XTI = (String) ((JSONObject) X.get(0)).getString("TIME");
        } catch (Exception E) {
        }

        final String FXT = XT;
        final float COORDX = ACOORDX, COORDY = ACOORDY;

        Display newDisplay = getWindowManager().getDefaultDisplay();
        int width = newDisplay.getWidth();

        //rname과 이름같은 식당 찾기

        ArrayList<ml.diony.motg.Display.MenuGroup> DataList = new ArrayList<ml.diony.motg.Display.MenuGroup>();
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.mylist);
        for (int i = 0; i < XM.length(); i++) {

            String XS = "";
            String XG = "";

            try {

                XS = (String) ((JSONArray) XM.get(i)).get(0);
                XG = (String) ((JSONArray) XM.get(i)).get(1);

            } catch (Exception E) {
            }

            MenuGroup TMP = new MenuGroup(XS);
            TMP.child.add("가격: " + XG + "원");
            DataList.add(TMP);

        }

        ml.diony.motg.Display.ExpandAdapter adapter = new ml.diony.motg.Display.ExpandAdapter(getApplicationContext(), R.layout.menu_row, R.layout.detail_row, DataList);
        listView.setIndicatorBounds(width - 50, width); //이 코드를 지우면 화살표 위치가 바뀐다.
        listView.setAdapter(adapter);

        Button bt1 = (Button) findViewById(R.id.button);
        bt1.setText("전화걸기");
        Button bt2 = (Button) findViewById(R.id.button2);
        bt2.setText("지도보기");
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + FXT)));
            }
        });

        Log.i("ABEFOREMAPMAPMAP", "COORDX = " + ACOORDX + ", COORDY = " + ACOORDY);
        Log.i("BEFOREMAPMAPMAP", "COORDX = " + COORDX + ", COORDY = " + COORDY);

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResInfoActivity.this, MapActivity.class);
                intent.putExtra("COORDX", COORDX);
                intent.putExtra("COORDY", COORDY);
                startActivity(intent);
            }
        });

        TextView tv1 = (TextView) findViewById(R.id.textView2);

        tv1.setText("영업 시간 : " + XTI);

/*
        Button bt3 = (Button) findViewById(R.id.button3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText date = (EditText) findViewById(R.id.date_text);
                int intdate = Integer.parseInt(date.getText().toString());
                //history로 넘긴다...
                }
            }
        });
        */

    }

}