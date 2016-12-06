package ml.diony.motg.Display;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;

import ml.diony.motg.R;

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
        } catch(Exception E) {}

        Log.i("ResListActivity", "와아아아아" + X.toString());
        //String rtype = intent.getExtras().getString("rtype");

        Rlist = new ArrayList<ResList>();
/*
        ResList rlist;
        //반복문으로 rtype과 같은 업종인 식당 불러옴
        while() { //확인안한 식당 있는경우
            //업종이 rtype과 같으면 rlist 정보받아오고 Rlist에 추가
            rlist.ResName = ;//받아온 이름
            rlist.code = ;//받아온 아이디
            Rlist.add(rlist);
        }
*/
        for(int i = 0; i < X.length(); i++) {

            String NAME = "";
            String MENU = "";

            try {

                NAME = (String) ((JSONArray) X.get(i)).get(0);
                MENU = (String) ((JSONArray) X.get(i)).get(1);

            } catch(Exception E) {}

            ResList rlist = new ResList(MENU + "-" + NAME);
            rlist.code = NAME;
            Rlist.add(rlist);

        }


        TodayRankingAdapter myAdapter = new TodayRankingAdapter(this, R.layout.todayres_row, Rlist);

        ListView myList;
        myList = (ListView) findViewById(R.id.todaylist);
        myList.setAdapter(myAdapter);
    }
}
