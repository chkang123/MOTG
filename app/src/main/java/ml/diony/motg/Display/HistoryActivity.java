package ml.diony.motg.Display;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.ExpandableListView;

import org.json.JSONObject;

import java.util.ArrayList;

import ml.diony.motg.Communication.Sync;
import ml.diony.motg.R;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Display newDisplay = getWindowManager().getDefaultDisplay();
        int width = newDisplay.getWidth();

        ArrayList<HistoryList> DataList = new ArrayList<HistoryList>();
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.history_list);

        //history 받아옴

        Sync X = new Sync();

        int RE = X.getREVINFO();

        for(int i = (RE > 30) ? RE - 30 : 1; i <= RE; i++) {

            JSONObject XX = X.getHISTORY(i);

            try {

                HistoryList hlist = new HistoryList(XX.getString("DATE"));

                hlist.child.add(XX.getString("MENU") + "-"+ XX.getString("RES"));
                DataList.add(hlist);

            } catch (Exception E) {}

        }

        HistoryAdapter adapter = new HistoryAdapter(getApplicationContext(), R.layout.menu_row, R.layout.historychild_row, DataList);
        listView.setIndicatorBounds(width - 50, width); //이 코드를 지우면 화살표 위치가 바뀐다.
        listView.setAdapter(adapter);
    }
}
