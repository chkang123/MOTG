package ml.diony.motg.Display;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ml.diony.motg.Communication.Interaction;
import ml.diony.motg.R;

/**
 * Created by KangChanghoon on 2016. 12. 3..
 */

//오늘의 추천 메뉴에서 추천 받은 메뉴-식당들의 리스트를 일정한 형태로
//출력하기 위해 사용하는 Class이다.
public class TodayRankingAdapter extends BaseAdapter {
    Context maincon;
    LayoutInflater inflater;
    ArrayList<ResList> arSrc;
    int layout;

    public TodayRankingAdapter(Context context, int alayout, ArrayList<ResList> aarSrc) {
        maincon = context;
        arSrc = aarSrc;
        layout = alayout;
        // ListView에서 사용한 View를 정의한 xml 를 읽어오기 위해
        // LayoutInfalater 객체를 생성
        inflater = LayoutInflater.from(maincon);
    }

    public int getCount() {
        return arSrc.size();
    }

    public Object getItem(int position) {
        return arSrc.get(position).ResName;
    }

    public long getItemId(int position) {
        return position;
    }

    // 각 항목의 뷰 생성
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        //리스트에 출력할 형식에 맞추어 TextView의 Text를 설정한다.
        TextView txt1 = (TextView) convertView.findViewById(R.id.todaymenu_ranking);
        txt1.setText(pos + 1 + "위 ");

        TextView txt = (TextView) convertView.findViewById(R.id.todaymenu_name);
        txt.setText(arSrc.get(pos).ResName);

        //각 메뉴 옆의 버튼을 누르면 해당 식당의 정보를 출력하는 화면으로 넘어가도록 버튼을 설정해준다.
        Button btn = (Button) convertView.findViewById(R.id.open_todayresinfo);
        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(maincon, ResInfoActivity.class);
                intent.putExtra("name", arSrc.get(pos).code);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                (new Interaction((Activity) maincon, maincon)).getSpecified("ALL", "NAME", arSrc.get(pos).code, intent);
            }
        });
        return convertView;
    }
}