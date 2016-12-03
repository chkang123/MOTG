package com.example.kangchanghoon.oop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
//업종별 식당 화면 출력
public class ReslistActivity extends AppCompatActivity {
    ArrayList<ResList> Rlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reslist);

        Intent intent = getIntent();
        String rtype = intent.getExtras().getString("rtype");

        Rlist = new ArrayList<ResList>();
/*
        ResList rlist;
        //반복문으로 rtype과 같은 업종인 식당 불러옴
        while() { //확인안한 식당 있는경우
            //업종이 rtype과 같으면 rlist 정보받아오고 Rlist에 추가
            rlist.ResName = ;//받아온 이름
            rlist.id = ;//받아온 아이디
            Rlist.add(rlist);
        }
*/
        MyListAdapter myAdapter = new MyListAdapter(this, R.layout.res_row, Rlist);

        ListView myList;
        myList = (ListView) findViewById(R.id.list);
        myList.setAdapter(myAdapter);
    }
}
