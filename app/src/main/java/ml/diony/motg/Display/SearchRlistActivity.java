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

//검색기능을 통해 검색된 결과를 식당들의 리스트 형태로 출력해주는 Class이다.
public class SearchRlistActivity extends AppCompatActivity {
    ArrayList<ml.diony.motg.Display.ResList> Rlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_rlist);

        Intent intent = getIntent();
        String ALLX = intent.getExtras().getString("ALLX");

        Log.i("RLA", ALLX);

        JSONArray X = new JSONArray();
        try {
            X = new JSONArray(ALLX);
        } catch (Exception E) {
        }

        Log.i("ResListActivity", "와아아아아" + X.toString());

        Rlist = new ArrayList<ml.diony.motg.Display.ResList>();

        //검색 결과로 출력되어야할 음식점들을 모두 찾고 그 식당들의 이름을 모두 저장한다.
        for (int i = 0; i < X.length(); i++) {

            String NAME = "";

            try {

                NAME = ((JSONObject) X.get(i)).getString("NAME");

            } catch (Exception E) {
            }

            ResList rlist = new ResList(NAME);
            Rlist.add(rlist);

        }

        ml.diony.motg.Display.MyListAdapter myAdapter = new ml.diony.motg.Display.MyListAdapter(this, R.layout.res_row, Rlist);

        ListView myList;
        myList = (ListView) findViewById(R.id.list);
        myList.setAdapter(myAdapter);
    }
}
