package ml.diony.motg.Display;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import ml.diony.motg.R;

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
            rlist.code = ;//받아온 아이디
            Rlist.add(rlist);
        }
*/

        ResList rlist1 = new ResList("asd");
        rlist1.ResName = "라이스스토리";
        rlist1.code = "A001";
        Rlist.add(rlist1);
        ResList rlist2 = new ResList("asd");
        rlist2.ResName = "샤브향";
        rlist2.code = "A000";
        Rlist.add(rlist2);
        ResList rlist3 = new ResList("asd");
        rlist3.ResName = "나인로드피제리아";
        rlist3.code = "A003";
        Rlist.add(rlist3);
        ResList rlist4 = new ResList("asd");
        rlist4.ResName = "다낭";
        rlist4.code = "A002";
        Rlist.add(rlist4);
        ResList rlist5 = new ResList("asd");
        rlist5.ResName = "등촌샤브칼국수";
        rlist5.code = "A004";
        Rlist.add(rlist5);

        ml.diony.motg.Display.MyListAdapter myAdapter = new ml.diony.motg.Display.MyListAdapter(this, R.layout.res_row, Rlist);

        ListView myList;
        myList = (ListView) findViewById(R.id.list);
        myList.setAdapter(myAdapter);
    }
}
