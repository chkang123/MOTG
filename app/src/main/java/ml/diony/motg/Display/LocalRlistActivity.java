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

//어떤 지역에 해당하는 버튼을 클릭했을때 실행되고, 그 지역에 위치한 식당들을 모두
//출력해주는 Class이다.
public class LocalRlistActivity extends AppCompatActivity {
    ArrayList<ResList> Rlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_rlist);

        Intent intent = getIntent();
        String location = intent.getExtras().getString("location");
        String ALLX = intent.getExtras().getString("ALLX");

        Log.i("RLA", ALLX);

        JSONArray X = new JSONArray();
        try {
            X = new JSONArray(ALLX);
        } catch (Exception E) {
        }

        Log.i("ResListActivity", "와아아아아" + X.toString());

        Rlist = new ArrayList<ResList>();


        //찾고자하는 지역과 같은 위치정보를 가진 식당들을 하나씩 비교하며 찾아낸다.
        for (int i = 0; i < X.length(); i++) {

            String NAME = "";

            try {

                NAME = ((JSONObject) X.get(i)).getString("NAME");

            } catch (Exception E) {
            }

            ResList rlist = new ResList(NAME);
            Rlist.add(rlist);

        }

        //선택된 식당들의 리스트를 출력하기 위해 MyListAdapter를 통하여 정리해준다.
        MyListAdapter myAdapter = new MyListAdapter(this, R.layout.res_row, Rlist);

        ListView myList;
        myList = (ListView) findViewById(R.id.local_list);
        myList.setAdapter(myAdapter);
    }
}
