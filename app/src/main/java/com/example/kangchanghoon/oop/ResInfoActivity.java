package com.example.kangchanghoon.oop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ExpandableListView;
import java.util.ArrayList;

import android.net.Uri;
import android.widget.Button;

public class ResInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_info);

        Intent intent = getIntent();
        String rname = intent.getExtras().getString("name");

        Display newDisplay = getWindowManager().getDefaultDisplay();
        int width = newDisplay.getWidth();

        //rname과 이름같은 식당 찾기

        ArrayList<MenuGroup> DataList = new ArrayList<MenuGroup>();
        ExpandableListView listView = (ExpandableListView)findViewById(R.id.mylist);
        MenuGroup temp = new MenuGroup("된장찌개");
        temp.child.add("가격: 5000원");
        DataList.add(temp);
        temp = new MenuGroup("순두부찌개");
        temp.child.add("가격: 60000원");
        DataList.add(temp);
        temp = new MenuGroup("김치찌개");
        temp.child.add("가격: 5000원");
        DataList.add(temp);

        ExpandAdapter adapter = new ExpandAdapter(getApplicationContext(),R.layout.menu_row,R.layout.detail_row,DataList);
        listView.setIndicatorBounds(width-50, width); //이 코드를 지우면 화살표 위치가 바뀐다.
        listView.setAdapter(adapter);

        Button bt1 = (Button) findViewById(R.id.button);
        bt1.setText("전화걸기");
        Button bt2 = (Button) findViewById(R.id.button2);
        bt2.setText("지도보기");
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.DIAL",Uri.parse("tel:010-4113-5492")));
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ResInfoActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });
/*
        Button bt3 = (Button) findViewById(R.id.button3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText date = (EditText) findViewById(R.id.date_text);
                int intdate = Integer.parseInt(date.getText().toString());
                //history로 넘긴다...
                }
            }
        });
        */

    }

}