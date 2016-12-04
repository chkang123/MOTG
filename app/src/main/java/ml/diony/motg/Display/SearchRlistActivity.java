package ml.diony.motg.Display;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ml.diony.motg.R;

public class SearchRlistActivity extends AppCompatActivity {
    ArrayList<ml.diony.motg.Display.ResList> Rlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rlist);

        Intent intent = getIntent();
        String keyword = intent.getExtras().getString("//////");
        String ALLX = intent.getExtras().getString("ALLX");

        Log.i("RLA", ALLX);

        JSONArray X = new JSONArray();
        try {
            X = new JSONArray(ALLX);
        } catch(Exception E) {}

        Log.i("ResListActivity", "와아아아아" + X.toString());

        Rlist = new ArrayList<ml.diony.motg.Display.ResList>();
/*
        ResList rlist;
        //반복문으로 keyword와 유사한 이름 식당 불러옴
        while() { //확인안한 식당 있는경우
            //비교후 rlist 정보받아오고 Rlist에 추가
            rlist.ResName = ;//받아온 이름
            rlist.id = ;//받아온 아이디
            Rlist.add(rlist);
        }
*/
        for(int i = 0; i < X.length(); i++) {

            String NAME = "";

            try {

                NAME = ((JSONObject) X.get(i)).getString("NAME");

            } catch(Exception E) {}

            ResList rlist = new ResList(NAME);
            Rlist.add(rlist);

        }

        ml.diony.motg.Display.MyListAdapter myAdapter = new ml.diony.motg.Display.MyListAdapter(this, R.layout.res_row, Rlist);

        ListView myList;
        myList = (ListView) findViewById(R.id.list);
        myList.setAdapter(myAdapter);
    }
}
