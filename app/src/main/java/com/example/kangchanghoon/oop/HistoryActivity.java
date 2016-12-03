package com.example.kangchanghoon.oop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Display newDisplay = getWindowManager().getDefaultDisplay();
        int width = newDisplay.getWidth();

        //history 받아옴

        ArrayList<HistoryList> DataList = new ArrayList<HistoryList>();
        ExpandableListView listView = (ExpandableListView)findViewById(R.id.history_list);

        HistoryAdapter adapter = new HistoryAdapter(getApplicationContext(),R.layout.menu_row,R.layout.historychild_row,DataList);
        listView.setIndicatorBounds(width-50, width); //이 코드를 지우면 화살표 위치가 바뀐다.
        listView.setAdapter(adapter);
    }
}
