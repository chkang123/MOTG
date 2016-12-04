package ml.diony.motg.Display;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import ml.diony.motg.R;

public class LocalRlistActivity extends AppCompatActivity {
    ArrayList<ResList> Rlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_rlist);

        Intent intent = getIntent();
        String location = intent.getExtras().getString("location");

        Rlist = new ArrayList<ResList>();

        ResList rlist1 = new ResList("asd");
        rlist1.ResName = "화통삼이동점";
        rlist1.code = "A001";
        Rlist.add(rlist1);
        ResList rlist2 = new ResList("asd");
        rlist2.ResName = "등촌샤브칼국수";
        rlist2.code = "A000";
        Rlist.add(rlist2);
        ResList rlist3 = new ResList("asd");
        rlist3.ResName = "육풍";
        rlist3.code = "A003";
        Rlist.add(rlist3);
        ResList rlist4 = new ResList("asd");
        rlist4.ResName = "진갈매기";
        rlist4.code = "A002";
        Rlist.add(rlist4);
        ResList rlist5 = new ResList("asd");
        rlist5.ResName = "바보형제쭈꾸미";
        rlist5.code = "A004";
        Rlist.add(rlist5);


/*
        ResList rlist;
        //반복문으로 keyword와 유사한 이름 식당 불러옴
        while() { //확인안한 식당 있는경우
            //비교후 rlist 정보받아오고 Rlist에 추가
            rlist.ResName = ;//받아온 이름
            rlist.id = ;//받아온 아이디
            Rlist.add(rlist);
        }
*/
        MyListAdapter myAdapter = new MyListAdapter(this, R.layout.res_row, Rlist);

        ListView myList;
        myList = (ListView) findViewById(R.id.local_list);
        myList.setAdapter(myAdapter);
    }
}
