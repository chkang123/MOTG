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

//업종별 식당 화면 출력
public class ReslistActivity extends AppCompatActivity {
    ArrayList<ResList> Rlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reslist);

        Intent intent = getIntent();
        String rtype = intent.getExtras().getString("rtype");
        String ALLX = intent.getExtras().getString("ALLX");

        Log.i("RLA", ALLX);

        JSONArray X = new JSONArray();
        try {
            X = new JSONArray(ALLX);
        } catch(Exception E) {}

        Log.i("ResListActivity", "와아아아아" + X.toString());

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
