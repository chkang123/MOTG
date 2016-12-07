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

//선택된 업종에 해당하는 식당들의 목록을 보여주는 화면을 출력하는 Class
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
        } catch (Exception E) {
        }

        Log.i("ResListActivity", "와아아아아" + X.toString());

        Rlist = new ArrayList<ResList>();

        //해당하는 식당들을 찾고 선택된 식당들의 이름을 모두 저장한다.
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
