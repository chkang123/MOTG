package ml.diony.motg.Display;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;

import ml.diony.motg.R;

//알고리즘을 통해 생성된 오늘의 추천메뉴 순위 상위 4개의 식당목록을 출력하는 Class이다.
public class TodaymenuActivity extends AppCompatActivity {
    ArrayList<ResList> Rlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todaymenu);

        Intent intent = getIntent();
        String ALLX = intent.getExtras().getString("ALLX");

        Log.i("RLA", ALLX);

        JSONArray X = new JSONArray();
        try {
            X = new JSONArray(ALLX);
        } catch (Exception E) {
        }

        Log.i("ResListActivity", "와아아아아" + X.toString());

        Rlist = new ArrayList<ResList>();

        //추천된 식당과 메뉴들에 대한 정보를 받아와 저장하고 출력한다.
        for (int i = 0; i < X.length(); i++) {

            String NAME = "";
            String MENU = "";

            try {

                NAME = (String) ((JSONArray) X.get(i)).get(0);
                MENU = (String) ((JSONArray) X.get(i)).get(1);

            } catch (Exception E) {
            }

            ResList rlist = new ResList(MENU + "-" + NAME);
            rlist.code = NAME;
            Rlist.add(rlist);

        }

        //TodayRankingAdapter를 이용해 원하는 형식으로 식당들의 리스트를 출력할 수 있도록 한다.
        TodayRankingAdapter myAdapter = new TodayRankingAdapter(this, R.layout.todayres_row, Rlist);

        ListView myList;
        myList = (ListView) findViewById(R.id.todaylist);
        myList.setAdapter(myAdapter);
    }
}
