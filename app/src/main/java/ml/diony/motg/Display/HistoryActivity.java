package ml.diony.motg.Display;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.ExpandableListView;

import org.json.JSONObject;

import java.util.ArrayList;

import ml.diony.motg.Communication.Sync;
import ml.diony.motg.R;

//방문기록을 이용하여 이미 방문하여 식사한 메뉴와 날짜 등을 출력하는 Class
//ExpandableListView를 이용하여 날짜별로 방문해 먹은 메뉴 식당이 출력된다.
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
        //받아온 정보를 DataList에 저장한다.
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

        //HistoryAdapter를 이용해 DataList에 저장된 데이터를 화면에 원하는 방식으로 띄울 수 있도록 해준다
        HistoryAdapter adapter = new HistoryAdapter(getApplicationContext(), R.layout.menu_row, R.layout.historychild_row, DataList);
        listView.setIndicatorBounds(width - 50, width); //이 코드를 지우면 화살표 위치가 바뀐다.
        listView.setAdapter(adapter);
    }
}
