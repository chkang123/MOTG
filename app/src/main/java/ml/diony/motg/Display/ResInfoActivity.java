package ml.diony.motg.Display;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import ml.diony.motg.R;

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

        ArrayList<ml.diony.motg.Display.MenuGroup> DataList = new ArrayList<ml.diony.motg.Display.MenuGroup>();
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.mylist);
        ml.diony.motg.Display.MenuGroup temp = new ml.diony.motg.Display.MenuGroup("된장찌개");
        temp.child.add("가격: 5000원");
        DataList.add(temp);
        temp = new ml.diony.motg.Display.MenuGroup("순두부찌개");
        temp.child.add("가격: 60000원");
        DataList.add(temp);
        temp = new ml.diony.motg.Display.MenuGroup("김치찌개");
        temp.child.add("가격: 5000원");
        DataList.add(temp);

        ml.diony.motg.Display.ExpandAdapter adapter = new ml.diony.motg.Display.ExpandAdapter(getApplicationContext(), R.layout.menu_row, R.layout.detail_row, DataList);
        listView.setIndicatorBounds(width - 50, width); //이 코드를 지우면 화살표 위치가 바뀐다.
        listView.setAdapter(adapter);

        Button bt1 = (Button) findViewById(R.id.button);
        bt1.setText("전화걸기");
        Button bt2 = (Button) findViewById(R.id.button2);
        bt2.setText("지도보기");
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:010-4113-5492")));
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResInfoActivity.this, MapActivity.class);
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