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

//선택된 식당의 정보를 출력하는 화면이다.
//식당의 전화번호로 바로연결, 지도보기, 식당의 메뉴 및 각각의 가격을 나타내주는 기능이 있다.
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
        //서버로부터 필요한 식당의 데이터들을 불러온다.
        JSONArray X = new JSONArray(), XM = new JSONArray();
        String XT = "", XTI = "";
        try {
            X = new JSONArray(ALLX);
            XM = ((JSONObject) X.get(0)).getJSONArray("MENU");
            XT = ((JSONObject) X.get(0)).getString("TEL");
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

        //메뉴와 가격들을 가지는 ExpandableListView에 출력할 데이터들을 불러와 저장한다.
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

        ml.diony.motg.Display.ExpandAdapter adapter = new ml.diony.motg.Display.ExpandAdapter(getApplicationContext(), R.layout.menu_row, R.layout.detail_row, DataList, rname);
        listView.setIndicatorBounds(width - 50, width); //이 코드를 지우면 화살표 위치가 바뀐다.
        listView.setAdapter(adapter);

        Button bt1 = (Button) findViewById(R.id.button);
        bt1.setText("전화걸기");
        Button bt2 = (Button) findViewById(R.id.button2);
        bt2.setText("지도보기");

        Log.i("FFFFFFFF", "WHYYY" + FXT);

        //식당의 전화번호로 전화를 걸 준비를 해준다.
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + FXT)));
            }
        });

        Log.i("ABEFOREMAPMAPMAP", "COORDX = " + ACOORDX + ", COORDY = " + ACOORDY);
        Log.i("BEFOREMAPMAPMAP", "COORDX = " + COORDX + ", COORDY = " + COORDY);

        //식당의 위치를 Daum지도 상에 나타내기 위해 MapActivity를 실행하며 식당의 위도, 경도 정보를 넘겨준다.
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

    }

}